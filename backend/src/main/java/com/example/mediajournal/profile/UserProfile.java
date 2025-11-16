package com.example.mediajournal.profile;

import com.example.mediajournal.shared.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_profiles")
public class UserProfile extends BaseEntity {

  @Column(name = "display_name", nullable = false)
  private String displayName;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserRole role = UserRole.GENERAL;

  @Column(name = "bio")
  private String bio;

  @Column(name = "avatar_url")
  private String avatarUrl;

  @Column(name = "stats", columnDefinition = "jsonb")
  private String stats = "{}";

  @Enumerated(EnumType.STRING)
  @Column(name = "badge_status", nullable = false)
  private BadgeStatus badgeStatus = BadgeStatus.NONE;
}
