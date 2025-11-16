package com.example.mediajournal.entries.dto;

import com.example.mediajournal.entries.ViewingEntryVisibility;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public record ViewingEntryResponse(
    UUID id,
    UUID userId,
    UUID workId,
    Instant watchedAt,
    Integer score,
    ViewingEntryVisibility visibility,
    String reviewBody,
    Set<String> tags,
    List<QuoteHighlightPayload> highlights) {}
