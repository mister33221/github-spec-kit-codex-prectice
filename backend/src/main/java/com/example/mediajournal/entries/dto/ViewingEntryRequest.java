package com.example.mediajournal.entries.dto;

import com.example.mediajournal.entries.ViewingEntryVisibility;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public record ViewingEntryRequest(
    @NotNull UUID userId,
    @NotNull UUID workId,
    Instant watchedAt,
    @NotNull @Min(1) @Max(10) Integer score,
    ViewingEntryVisibility visibility,
    String reviewBody,
    Set<String> tags,
    @Valid List<QuoteHighlightPayload> highlights) {}
