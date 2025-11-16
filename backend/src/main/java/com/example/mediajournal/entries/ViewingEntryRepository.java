package com.example.mediajournal.entries;

import com.example.mediajournal.entries.projection.TagUsageProjection;
import com.example.mediajournal.entries.projection.VisibilityCountProjection;
import com.example.mediajournal.shared.repository.BaseRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ViewingEntryRepository extends BaseRepository<ViewingEntry, UUID> {
  List<ViewingEntry> findAllByUserIdOrderByWatchedAtDesc(UUID userId);

  long countByUserId(UUID userId);

  @Query("select avg(v.score) from ViewingEntry v where v.userId = :userId")
  Double findAverageScoreByUserId(@Param("userId") UUID userId);

  @Query(
      "select v.visibility as visibility, count(v) as count "
          + "from ViewingEntry v where v.userId = :userId group by v.visibility")
  List<VisibilityCountProjection> countByVisibility(@Param("userId") UUID userId);

  @Query(
      "select tag as tag, count(tag) as count "
          + "from ViewingEntry v join v.tags tag "
          + "where v.userId = :userId group by tag order by count(tag) desc")
  List<TagUsageProjection> findTopTags(@Param("userId") UUID userId);
}
