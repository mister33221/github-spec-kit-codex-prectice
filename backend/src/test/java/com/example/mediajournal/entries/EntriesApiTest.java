package com.example.mediajournal.entries;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.mediajournal.entries.dto.QuoteHighlightPayload;
import com.example.mediajournal.entries.dto.ViewingEntryRequest;
import com.example.mediajournal.entries.dto.ViewingEntryResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EntriesApiTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private JdbcTemplate jdbcTemplate;
  @Autowired private ObjectMapper objectMapper;
  @Autowired private ViewingEntryRepository repository;

  private UUID userId;
  private UUID workId;

  @BeforeEach
  void setUp() {
    jdbcTemplate.execute("DELETE FROM entry_tags");
    jdbcTemplate.execute("DELETE FROM quote_highlights");
    jdbcTemplate.execute("DELETE FROM viewing_entries");
    jdbcTemplate.execute("DELETE FROM tags");
    jdbcTemplate.execute("DELETE FROM works");
    jdbcTemplate.execute("DELETE FROM user_profiles");

    userId =
        jdbcTemplate.queryForObject(
            "INSERT INTO user_profiles (display_name, role) VALUES ('測試用戶','GENERAL') RETURNING id",
            UUID.class);
    workId =
        jdbcTemplate.queryForObject(
            "INSERT INTO works (title, release_year) VALUES ('測試作品',2024) RETURNING id", UUID.class);
    jdbcTemplate.update(
        "INSERT INTO tags (slug, type, label) VALUES ('sci-fi','GENRE','科幻'), ('night','CUSTOM','夜晚')");
  }

  @Test
  void createAndFetchEntry() throws Exception {
    ViewingEntryRequest request =
        new ViewingEntryRequest(
            userId,
            workId,
            Instant.parse("2024-01-01T12:00:00Z"),
            9,
            ViewingEntryVisibility.PUBLIC,
            "很棒的電影",
            Set.of("sci-fi", "night"),
            List.of(new QuoteHighlightPayload("台詞", "Quote", "01:00:00")));

    String responseBody =
        mockMvc
            .perform(
                post("/api/entries")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getContentAsString();

    ViewingEntryResponse response =
        objectMapper.readValue(responseBody, ViewingEntryResponse.class);

    assertThat(response.id()).isNotNull();
    assertThat(response.tags()).containsExactlyInAnyOrder("sci-fi", "night");
    assertThat(response.highlights()).hasSize(1);

    String listResponse =
        mockMvc
            .perform(get("/api/entries").param("userId", userId.toString()))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

    ViewingEntryResponse[] entries =
        objectMapper.readValue(listResponse, ViewingEntryResponse[].class);
    assertThat(entries).hasSize(1);
    assertThat(entries[0].reviewBody()).isEqualTo("很棒的電影");
  }

  @Test
  void updateEntry() throws Exception {
    ViewingEntryRequest request =
        new ViewingEntryRequest(
            userId,
            workId,
            Instant.now(),
            6,
            ViewingEntryVisibility.PRIVATE,
            "初始心得",
            Set.of("sci-fi"),
            List.of());

    ViewingEntryResponse created =
        objectMapper.readValue(
            mockMvc
                .perform(
                    post("/api/entries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ViewingEntryResponse.class);

    ViewingEntryRequest patchRequest =
        new ViewingEntryRequest(
            created.userId(),
            created.workId(),
            created.watchedAt(),
            8,
            ViewingEntryVisibility.PUBLIC,
            "更新心得",
            Set.of("night"),
            List.of());

    mockMvc
        .perform(
            patch("/api/entries/{id}", created.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patchRequest)))
        .andExpect(status().isOk());

    ViewingEntry updated =
        repository.findById(created.id()).orElseThrow();
    assertThat(updated.getScore()).isEqualTo(8);
    assertThat(updated.getReviewBody()).isEqualTo("更新心得");
    assertThat(updated.getTags()).containsExactly("night");
  }
}
