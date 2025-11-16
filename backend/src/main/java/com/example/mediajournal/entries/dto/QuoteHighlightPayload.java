package com.example.mediajournal.entries.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record QuoteHighlightPayload(
    @NotBlank @Size(max = 280) String originalText, String translatedText, String timestampHint) {}
