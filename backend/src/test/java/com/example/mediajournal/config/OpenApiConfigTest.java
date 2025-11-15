package com.example.mediajournal.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpenApiConfigTest {

  private final OpenApiConfig config = new OpenApiConfig();

  @Test
  void mediaJournalOpenAPIBuildsMetadata() {
    var openApi = config.mediaJournalOpenAPI("1.2.3");

    assertThat(openApi.getInfo().getTitle()).isEqualTo("影視觀影紀錄社群平台 API");
    assertThat(openApi.getInfo().getVersion()).isEqualTo("1.2.3");
    assertThat(openApi.getInfo().getDescription()).contains("Spec Kit");
  }
}
