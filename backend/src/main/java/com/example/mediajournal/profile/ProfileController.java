package com.example.mediajournal.profile;

import com.example.mediajournal.profile.dto.ProfileSummaryResponse;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

  private final ProfileSummaryService profileSummaryService;

  public ProfileController(ProfileSummaryService profileSummaryService) {
    this.profileSummaryService = profileSummaryService;
  }

  @GetMapping("/{profileId}")
  public ProfileSummaryResponse getProfile(@PathVariable UUID profileId) {
    return profileSummaryService.getSummary(profileId);
  }
}
