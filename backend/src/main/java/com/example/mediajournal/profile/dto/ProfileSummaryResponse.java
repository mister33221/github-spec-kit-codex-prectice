package com.example.mediajournal.profile.dto;

import com.example.mediajournal.entries.ViewingEntryVisibility;
import com.example.mediajournal.profile.BadgeStatus;
import com.example.mediajournal.profile.UserRole;
import java.util.List;
import java.util.UUID;

public record ProfileSummaryResponse(
    UUID profileId,
    String displayName,
    UserRole role,
    BadgeStatus badgeStatus,
    String bio,
    String avatarUrl,
    long entryCount,
    double averageScore,
    List<TagUsage> topTags,
    List<VisibilitySlice> visibility) {

  public record TagUsage(String tag, long count) {}

  public record VisibilitySlice(ViewingEntryVisibility visibility, long count) {}
}
