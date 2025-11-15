CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TYPE role_type AS ENUM ('GENERAL', 'PROFESSIONAL', 'ADMIN');
CREATE TYPE badge_status_type AS ENUM ('NONE', 'PENDING', 'APPROVED');
CREATE TYPE visibility_type AS ENUM ('PUBLIC', 'FOLLOWERS', 'PRIVATE');
CREATE TYPE tag_type AS ENUM ('GENRE', 'YEAR', 'LANGUAGE', 'CUSTOM');
CREATE TYPE discussion_status_type AS ENUM ('ACTIVE', 'LOCKED');
CREATE TYPE reply_status_type AS ENUM ('ACTIVE', 'TEMP_REMOVED', 'REMOVED');
CREATE TYPE moderation_target_type AS ENUM ('THREAD_REPLY', 'THREAD');
CREATE TYPE moderation_status_type AS ENUM ('PENDING', 'UNDER_REVIEW', 'RESOLVED', 'REJECTED');
CREATE TYPE notification_type AS ENUM ('NEW_ENTRY', 'NEW_REPLY', 'MODERATION_UPDATE');
CREATE TYPE focus_frequency_type AS ENUM ('INSTANT', 'DAILY', 'WEEKLY');

CREATE TABLE user_profiles (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    display_name TEXT NOT NULL,
    role role_type NOT NULL DEFAULT 'GENERAL',
    bio TEXT,
    avatar_url TEXT,
    stats JSONB NOT NULL DEFAULT '{}'::jsonb,
    badge_status badge_status_type NOT NULL DEFAULT 'NONE',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE works (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title TEXT NOT NULL,
    release_year INTEGER,
    official_rating NUMERIC(4,2),
    genres TEXT[] NOT NULL DEFAULT '{}',
    language TEXT,
    cover_url TEXT,
    version_tag TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE tags (
    slug TEXT PRIMARY KEY,
    type tag_type NOT NULL,
    label TEXT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE viewing_entries (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES user_profiles(id) ON DELETE CASCADE,
    work_id UUID NOT NULL REFERENCES works(id) ON DELETE CASCADE,
    watched_at TIMESTAMPTZ,
    score SMALLINT NOT NULL CHECK (score BETWEEN 1 AND 10),
    visibility visibility_type NOT NULL DEFAULT 'PUBLIC',
    review_body TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE entry_tags (
    entry_id UUID NOT NULL REFERENCES viewing_entries(id) ON DELETE CASCADE,
    tag_slug TEXT NOT NULL REFERENCES tags(slug) ON DELETE CASCADE,
    PRIMARY KEY (entry_id, tag_slug)
);

CREATE TABLE quote_highlights (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    entry_id UUID NOT NULL REFERENCES viewing_entries(id) ON DELETE CASCADE,
    original_text TEXT NOT NULL,
    translated_text TEXT,
    timestamp_hint VARCHAR(32),
    char_length INTEGER CHECK (char_length <= 280),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE discussion_threads (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    work_id UUID REFERENCES works(id) ON DELETE CASCADE,
    tag_slug TEXT REFERENCES tags(slug) ON DELETE SET NULL,
    title TEXT NOT NULL,
    body TEXT NOT NULL,
    author_id UUID NOT NULL REFERENCES user_profiles(id) ON DELETE CASCADE,
    status discussion_status_type NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE moderation_cases (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    target_type moderation_target_type NOT NULL,
    target_id UUID NOT NULL,
    status moderation_status_type NOT NULL DEFAULT 'PENDING',
    opened_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    closed_at TIMESTAMPTZ,
    notes TEXT
);

CREATE TABLE discussion_replies (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    thread_id UUID NOT NULL REFERENCES discussion_threads(id) ON DELETE CASCADE,
    parent_id UUID REFERENCES discussion_replies(id) ON DELETE CASCADE,
    author_id UUID NOT NULL REFERENCES user_profiles(id) ON DELETE CASCADE,
    body TEXT NOT NULL,
    quoted_text TEXT,
    status reply_status_type NOT NULL DEFAULT 'ACTIVE',
    moderation_case_id UUID REFERENCES moderation_cases(id) ON DELETE SET NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE focus_subscriptions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    follower_id UUID NOT NULL REFERENCES user_profiles(id) ON DELETE CASCADE,
    author_id UUID NOT NULL REFERENCES user_profiles(id) ON DELETE CASCADE,
    notification_frequency focus_frequency_type NOT NULL DEFAULT 'INSTANT',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    UNIQUE (follower_id, author_id)
);

CREATE TABLE notifications (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    recipient_id UUID NOT NULL REFERENCES user_profiles(id) ON DELETE CASCADE,
    type notification_type NOT NULL,
    payload JSONB NOT NULL DEFAULT '{}'::jsonb,
    read_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE quote_favorites (
    quote_id UUID NOT NULL REFERENCES quote_highlights(id) ON DELETE CASCADE,
    user_id UUID NOT NULL REFERENCES user_profiles(id) ON DELETE CASCADE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    PRIMARY KEY (quote_id, user_id)
);

CREATE INDEX idx_viewing_entries_work ON viewing_entries(work_id);
CREATE INDEX idx_viewing_entries_user ON viewing_entries(user_id);
CREATE INDEX idx_discussion_threads_work ON discussion_threads(work_id);
CREATE INDEX idx_discussion_replies_thread ON discussion_replies(thread_id);
CREATE INDEX idx_notifications_recipient ON notifications(recipient_id);
CREATE INDEX idx_focus_subscriptions_author ON focus_subscriptions(author_id);

CREATE VIEW aggregate_scores AS
SELECT
    ve.work_id,
    AVG(CASE WHEN up.role = 'GENERAL' THEN ve.score END) AS avg_general_score,
    AVG(CASE WHEN up.role = 'PROFESSIONAL' THEN ve.score END) AS avg_pro_score,
    ROUND(((COALESCE(AVG(CASE WHEN up.role = 'PROFESSIONAL' THEN ve.score END), 0) * 2)
        + COALESCE(AVG(CASE WHEN up.role <> 'PROFESSIONAL' THEN ve.score END), 0)) / 3, 2) AS weighted_score
FROM viewing_entries ve
JOIN user_profiles up ON up.id = ve.user_id
GROUP BY ve.work_id;
