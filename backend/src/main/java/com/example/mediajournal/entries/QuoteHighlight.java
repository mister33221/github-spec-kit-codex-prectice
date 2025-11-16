package com.example.mediajournal.entries;

import com.example.mediajournal.shared.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "quote_highlights")
public class QuoteHighlight extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "entry_id", nullable = false)
  private ViewingEntry entry;

  @Column(name = "original_text", nullable = false, columnDefinition = "text")
  private String originalText;

  @Column(name = "translated_text", columnDefinition = "text")
  private String translatedText;

  @Column(name = "timestamp_hint")
  private String timestampHint;

  @Column(name = "char_length")
  private Integer charLength;
}
