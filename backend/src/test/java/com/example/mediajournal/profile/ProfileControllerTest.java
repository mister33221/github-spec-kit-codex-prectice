package com.example.mediajournal.profile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.mediajournal.entries.ViewingEntryVisibility;
import com.example.mediajournal.profile.dto.ProfileSummaryResponse;
import com.example.mediajournal.profile.dto.ProfileSummaryResponse.VisibilitySlice;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
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
class ProfileControllerTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private JdbcTemplate jdbcTemplate;
  @Autowired private ObjectMapper objectMapper;

  private UUID profileId;
  private UUID workId;

  @BeforeEach
  void setUp() {
    jdbcTemplate.execute("DELETE FROM entry_tags");
    jdbcTemplate.execute("DELETE FROM quote_highlights");
    jdbcTemplate.execute("DELETE FROM viewing_entries");
    jdbcTemplate.execute("DELETE FROM tags");
    jdbcTemplate.execute("DELETE FROM works");
    jdbcTemplate.execute("DELETE FROM user_profiles");

    profileId =
        jdbcTemplate.queryForObject(
            "INSERT INTO user_profiles (display_name, role, bio, avatar_url) "
                + "VALUES ('測試使用者','GENERAL','愛寫觀影心得','https://img/test.png') RETURNING id",
            UUID.class);

    workId =
        jdbcTemplate.queryForObject(
            "INSERT INTO works (title, release_year) VALUES ('測試影片', 2024) RETURNING id",
            UUID.class);

    jdbcTemplate.update(
        "INSERT INTO tags (slug, type, label) VALUES ('sci-fi','GENRE','科幻'), "
            + "('feel-good','CUSTOM','療癒')");

    UUID entryOne =
        jdbcTemplate.queryForObject(
            "INSERT INTO viewing_entries (user_id, work_id, watched_at, score, visibility, review_body) "
                + "VALUES (?,?,?,?,?,?) RETURNING id",
            UUID.class,
            profileId,
            workId,
            Timestamp.from(Instant.parse("2025-01-01T00:00:00Z")),
            8,
            ViewingEntryVisibility.PUBLIC.name(),
            "第一筆紀錄");
    jdbcTemplate.update(
        "INSERT INTO entry_tags (entry_id, tag_slug) VALUES (?, ?), (?, ?)",
        entryOne,
        "sci-fi",
        entryOne,
        "feel-good");

    UUID entryTwo =
        jdbcTemplate.queryForObject(
            "INSERT INTO viewing_entries (user_id, work_id, watched_at, score, visibility, review_body) "
                + "VALUES (?,?,?,?,?,?) RETURNING id",
            UUID.class,
            profileId,
            workId,
            Timestamp.from(Instant.parse("2025-01-02T00:00:00Z")),
            7,
            ViewingEntryVisibility.PRIVATE.name(),
            "第二筆紀錄");
    jdbcTemplate.update(
        "INSERT INTO entry_tags (entry_id, tag_slug) VALUES (?, ?)", entryTwo, "sci-fi");
  }

  @Test
  void shouldReturnProfileSummary() throws Exception {
    String responseBody =
        mockMvc
            .perform(get("/api/profile/{id}", profileId).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

    ProfileSummaryResponse response =
        objectMapper.readValue(responseBody, ProfileSummaryResponse.class);

    assertThat(response.profileId()).isEqualTo(profileId);
    assertThat(response.displayName()).isEqualTo("測試使用者");
    assertThat(response.entryCount()).isEqualTo(2);
    assertThat(response.averageScore()).isEqualTo(7.5d);
    assertThat(response.topTags()).hasSizeGreaterThanOrEqualTo(1);
    assertThat(response.topTags().get(0).tag()).isEqualTo("sci-fi");

    Map<ViewingEntryVisibility, Long> visibilityMap =
        response.visibility().stream()
            .collect(Collectors.toMap(VisibilitySlice::visibility, VisibilitySlice::count));

    assertThat(visibilityMap.get(ViewingEntryVisibility.PUBLIC)).isEqualTo(1L);
    assertThat(visibilityMap.get(ViewingEntryVisibility.PRIVATE)).isEqualTo(1L);
  }
}
