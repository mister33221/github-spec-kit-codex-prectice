package com.example.mediajournal.profile;

import com.example.mediajournal.entries.ViewingEntryRepository;
import com.example.mediajournal.entries.ViewingEntryVisibility;
import com.example.mediajournal.entries.projection.TagUsageProjection;
import com.example.mediajournal.entries.projection.VisibilityCountProjection;
import com.example.mediajournal.profile.dto.ProfileSummaryResponse;
import com.example.mediajournal.profile.dto.ProfileSummaryResponse.TagUsage;
import com.example.mediajournal.profile.dto.ProfileSummaryResponse.VisibilitySlice;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ProfileSummaryService {

  private static final int TOP_TAG_LIMIT = 5;

  private final UserProfileRepository userProfileRepository;
  private final ViewingEntryRepository viewingEntryRepository;

  public ProfileSummaryService(
      UserProfileRepository userProfileRepository, ViewingEntryRepository viewingEntryRepository) {
    this.userProfileRepository = userProfileRepository;
    this.viewingEntryRepository = viewingEntryRepository;
  }

  public ProfileSummaryResponse getSummary(UUID profileId) {
    UserProfile profile =
        userProfileRepository
            .findById(profileId)
            .orElseThrow(
                () -> new EntityNotFoundException("Profile %s not found".formatted(profileId)));

    long entryCount = viewingEntryRepository.countByUserId(profileId);
    double averageScore =
        formatAverageScore(viewingEntryRepository.findAverageScoreByUserId(profileId));

    List<TagUsage> tags = mapTopTags(profileId);
    List<VisibilitySlice> visibility = mapVisibilitySlices(profileId);

    return new ProfileSummaryResponse(
        profile.getId(),
        profile.getDisplayName(),
        profile.getRole(),
        profile.getBadgeStatus(),
        profile.getBio(),
        profile.getAvatarUrl(),
        entryCount,
        averageScore,
        tags,
        visibility);
  }

  private double formatAverageScore(Double rawAverage) {
    if (rawAverage == null) {
      return 0;
    }
    return BigDecimal.valueOf(rawAverage).setScale(2, RoundingMode.HALF_UP).doubleValue();
  }

  private List<TagUsage> mapTopTags(UUID profileId) {
    List<TagUsageProjection> projections = viewingEntryRepository.findTopTags(profileId);
    List<TagUsage> result = new ArrayList<>();
    for (int i = 0; i < projections.size() && i < TOP_TAG_LIMIT; i++) {
      TagUsageProjection projection = projections.get(i);
      result.add(new TagUsage(projection.getTag(), projection.getCount()));
    }
    return result;
  }

  private List<VisibilitySlice> mapVisibilitySlices(UUID profileId) {
    Map<ViewingEntryVisibility, Long> counts = new EnumMap<>(ViewingEntryVisibility.class);
    for (ViewingEntryVisibility visibility : ViewingEntryVisibility.values()) {
      counts.put(visibility, 0L);
    }
    List<VisibilityCountProjection> projectionList =
        viewingEntryRepository.countByVisibility(profileId);
    projectionList.forEach(
        projection -> counts.put(projection.getVisibility(), projection.getCount()));

    return counts.entrySet().stream()
        .map(entry -> new VisibilitySlice(entry.getKey(), entry.getValue()))
        .toList();
  }
}
