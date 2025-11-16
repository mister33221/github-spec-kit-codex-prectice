package com.example.mediajournal.entries;

import com.example.mediajournal.entries.dto.ViewingEntryRequest;
import com.example.mediajournal.entries.dto.ViewingEntryResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/entries")
public class EntriesController {

  private final ViewingEntryService service;

  public EntriesController(ViewingEntryService service) {
    this.service = service;
  }

  @GetMapping
  public List<ViewingEntryResponse> list(@RequestParam UUID userId) {
    return service.listByUser(userId);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ViewingEntryResponse create(@Valid @RequestBody ViewingEntryRequest request) {
    return service.create(request);
  }

  @PatchMapping("/{entryId}")
  public ViewingEntryResponse update(
      @PathVariable UUID entryId, @Valid @RequestBody ViewingEntryRequest request) {
    return service.update(entryId, request);
  }
}
