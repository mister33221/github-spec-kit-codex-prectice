package com.example.mediajournal.entries;

import com.example.mediajournal.shared.domain.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "viewing_entries")
public class ViewingEntry extends BaseEntity {

  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @Column(name = "work_id", nullable = false)
  private UUID workId;

  @Column(name = "watched_at")
  private Instant watchedAt;

  @Column(name = "score", nullable = false)
  private Integer score;

  @Enumerated(EnumType.STRING)
  @Column(name = "visibility", nullable = false)
  private ViewingEntryVisibility visibility = ViewingEntryVisibility.PUBLIC;

  @Column(name = "review_body", columnDefinition = "text")
  private String reviewBody;

  @ElementCollection
  @CollectionTable(name = "entry_tags", joinColumns = @JoinColumn(name = "entry_id"))
  @Column(name = "tag_slug")
  private Set<String> tags = new LinkedHashSet<>();

  @OneToMany(
      mappedBy = "entry",
      cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
      orphanRemoval = true)
  private List<QuoteHighlight> highlights = new ArrayList<>();
}
