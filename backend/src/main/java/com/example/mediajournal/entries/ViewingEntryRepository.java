package com.example.mediajournal.entries;

import com.example.mediajournal.shared.repository.BaseRepository;
import java.util.List;
import java.util.UUID;

public interface ViewingEntryRepository extends BaseRepository<ViewingEntry, UUID> {
  List<ViewingEntry> findAllByUserIdOrderByWatchedAtDesc(UUID userId);
}
