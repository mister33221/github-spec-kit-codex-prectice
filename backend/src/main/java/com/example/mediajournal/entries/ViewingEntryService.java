package com.example.mediajournal.entries;

import com.example.mediajournal.entries.dto.QuoteHighlightPayload;
import com.example.mediajournal.entries.dto.ViewingEntryRequest;
import com.example.mediajournal.entries.dto.ViewingEntryResponse;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ViewingEntryService {

  private final ViewingEntryRepository repository;

  public ViewingEntryService(ViewingEntryRepository repository) {
    this.repository = repository;
  }

  public List<ViewingEntryResponse> listByUser(UUID userId) {
    return repository.findAllByUserIdOrderByWatchedAtDesc(userId).stream()
        .map(this::toResponse)
        .toList();
  }

  @Transactional
  public ViewingEntryResponse create(ViewingEntryRequest request) {
    ViewingEntry entry = new ViewingEntry();
    copyRequest(entry, request, true);
    return toResponse(repository.save(entry));
  }

  @Transactional
  public ViewingEntryResponse update(UUID entryId, ViewingEntryRequest request) {
    ViewingEntry entry =
        repository
            .findById(entryId)
            .orElseThrow(
                () -> new EntityNotFoundException("Entry %s not found".formatted(entryId)));
    copyRequest(entry, request, false);
    return toResponse(entry);
  }

  private void copyRequest(ViewingEntry entry, ViewingEntryRequest request, boolean isCreate) {
    if (isCreate) {
      entry.setUserId(request.userId());
      entry.setWorkId(request.workId());
    }
    if (request.watchedAt() != null) {
      entry.setWatchedAt(request.watchedAt());
    }
    if (request.score() != null) {
      entry.setScore(request.score());
    }
    if (request.visibility() != null) {
      entry.setVisibility(request.visibility());
    }
    if (request.reviewBody() != null) {
      entry.setReviewBody(request.reviewBody());
    }
    if (request.tags() != null) {
      entry.getTags().clear();
      entry.getTags().addAll(request.tags());
    }
    if (request.highlights() != null) {
      entry.getHighlights().clear();
      request.highlights().forEach(payload -> entry.getHighlights().add(toEntity(payload, entry)));
    }
  }

  private QuoteHighlight toEntity(QuoteHighlightPayload payload, ViewingEntry entry) {
    QuoteHighlight highlight = new QuoteHighlight();
    highlight.setEntry(entry);
    highlight.setOriginalText(payload.originalText());
    highlight.setTranslatedText(payload.translatedText());
    highlight.setTimestampHint(payload.timestampHint());
    highlight.setCharLength(payload.originalText() != null ? payload.originalText().length() : 0);
    return highlight;
  }

  private ViewingEntryResponse toResponse(ViewingEntry entry) {
    List<QuoteHighlightPayload> highlightPayloads =
        entry.getHighlights().stream()
            .map(
                h ->
                    new QuoteHighlightPayload(
                        h.getOriginalText(), h.getTranslatedText(), h.getTimestampHint()))
            .collect(Collectors.toList());
    return new ViewingEntryResponse(
        entry.getId(),
        entry.getUserId(),
        entry.getWorkId(),
        entry.getWatchedAt(),
        entry.getScore(),
        entry.getVisibility(),
        entry.getReviewBody(),
        entry.getTags(),
        highlightPayloads);
  }
}
