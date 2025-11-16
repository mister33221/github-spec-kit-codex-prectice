package com.example.mediajournal.entries.projection;

import com.example.mediajournal.entries.ViewingEntryVisibility;

public interface VisibilityCountProjection {
  ViewingEntryVisibility getVisibility();

  long getCount();
}
