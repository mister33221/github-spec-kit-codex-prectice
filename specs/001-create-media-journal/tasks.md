# Tasks: 影視觀影紀錄社群平台

**Input**: Design documents from `/specs/001-create-media-journal/`
**Prerequisites**: plan.md, spec.md, research.md, data-model.md, contracts/

**Tests**: 依需求於各 User Story 安排必要的整合或 e2e 測試。

**Organization**: 任務依優先階段與 User Story 分組，確保可獨立交付與驗證。

## Phase 1: Setup（共同基礎）

- [ ] T001 建立 Spring Boot 專案骨架與模組目錄 `backend/src/main/java/com/example/mediajournal`
- [ ] T002 初始化 Angular 工作區並安裝 Tailwind/NgRx/Vitest `frontend/`
- [ ] T003 [P] 建立 `infrastructure/docker-compose.yml`（app + postgres + frontend）與 `.env.example`
- [ ] T004 [P] 設定 lint/formatter/git hooks（`backend/pom.xml`, `frontend/package.json`, `.husky/`）

---

## Phase 2: Foundational（阻塞前提）

- [ ] T005 建立 Flyway 基礎遷移（Work、UserProfile、Tag、Notification）`backend/src/main/resources/db/migration/V1__baseline.sql`
- [ ] T006 建立共用 domain/repository layer（`backend/src/main/java/com/example/mediajournal/shared/`）並串接 PostgreSQL/H2 profile
- [ ] T007 實作 JWT + RBAC + 審核稽核記錄 `backend/src/main/java/com/example/mediajournal/config/SecurityConfig.java`
- [ ] T008 [P] 建立全域錯誤處理與 API 版本設定 `backend/src/main/java/com/example/mediajournal/config/WebConfig.java`
- [ ] T009 [P] 建立 Angular core 模組（API client、auth guard、theme service）`frontend/src/app/core/`
- [ ] T010 [P] 建立 Tailwind design tokens 與深色/高對比/亮色主題 `frontend/src/styles/tailwind.config.ts`
- [ ] T011 建立觀測性基礎（Micrometer + OpenTelemetry +通知 SLA 指標）`backend/src/main/java/com/example/mediajournal/config/ObservabilityConfig.java`
- [ ] T012 設定 JaCoCo/ESLint 報告門檻並將 80% 覆蓋率檢查加入 CI 指令 `backend/pom.xml`, `frontend/package.json`

Checkpoint：完成後可進入各 User Story。

---

## Phase 3: User Story 1 - 建立個人觀影筆記（Priority P1，MVP）

**Goal**：新增/編輯觀影紀錄、標籤、引用金句，並於個人版面顯示。
**Independent Test**：於 2 分鐘內新增 + 編輯紀錄並更新個人版面（T018/T019 測試驗證）。

### Tests
- [ ] T013 [P] [US1] 撰寫 `/api/entries` 合約/整合測試 `backend/src/test/java/com/example/mediajournal/entries/EntriesApiTest.java`
- [ ] T014 [P] [US1] 撰寫前端 e2e（建立/編輯流程）`frontend/tests/e2e/entry-flow.spec.ts`

### Implementation
- [ ] T015 [P] [US1] 建立 ViewingEntry/QuoteHighlight/Tag Entities 與 repositories `backend/src/main/java/com/example/mediajournal/entries`
- [ ] T016 [US1] 實作 `ViewingEntryService`（引言長度驗證、標籤批次）`backend/src/main/java/com/example/mediajournal/entries/ViewingEntryService.java`
- [ ] T017 [US1] 實作 `/api/entries` Controller（GET/POST/PATCH）`backend/src/main/java/com/example/mediajournal/entries/EntriesController.java`
- [ ] T018 [P] [US1] 建立 Angular `entry-editor` module `frontend/src/app/features/entry-editor/`
- [ ] T019 [US1] 建立 entry timeline component 與統計圖卡 `frontend/src/app/features/entry-editor/components/entry-timeline.component.ts`
- [ ] T020 [US1] 實作 profile 統計/可見性 API `backend/src/main/java/com/example/mediajournal/profile/ProfileController.java`
- [ ] T021 [US1] 建立 `ProfileSummaryService`（平均評分、常用標籤、可見性設定）`backend/src/main/java/com/example/mediajournal/profile/ProfileSummaryService.java`
- [ ] T022 [US1] 前端個人版面設定頁（公開/半公開/私人）`frontend/src/app/features/profile/profile-visibility.component.ts`
- [ ] T023 [US1] 更新 quickstart：記錄建立/可見性驗證指引 `specs/001-create-media-journal/quickstart.md`

Checkpoint：MVP 可 demo。

---

## Phase 4: User Story 2 - 標籤討論與即時回覆（Priority P2）

**Goal**：在作品或標籤下建立討論串並即時互動。
**Independent Test**：兩帳號於同標籤互動且看見即時更新（T028/T029 驗證）。

### Tests
- [ ] T024 [P] [US2] `/api/discussions` + `/api/discussions/{id}/replies` 合約測試 `backend/src/test/java/com/example/mediajournal/discussions/DiscussionsApiTest.java`
- [ ] T025 [P] [US2] 前端 e2e（建立貼文、引用回覆、即時更新）`frontend/tests/e2e/discussion-thread.spec.ts`

### Implementation
- [ ] T026 [P] [US2] 建立 DiscussionThread/Reply/ModerationCase migrations `backend/src/main/resources/db/migration/V2__discussions.sql`
- [ ] T027 [US2] 實作 `DiscussionService`（層級回覆、引用原句）`backend/src/main/java/com/example/mediajournal/discussions/DiscussionService.java`
- [ ] T028 [US2] 實作討論 API + WebSocket/SSE 更新 `backend/src/main/java/com/example/mediajournal/discussions/DiscussionsController.java`
- [ ] T029 [P] [US2] 建立 Angular `discussion-board` module `frontend/src/app/features/discussion-board/`
- [ ] T030 [US2] 標籤聚合牆與引用 UI `frontend/src/app/features/discussion-board/components/tag-thread-view.component.ts`
- [ ] T031 [US2] 通知客戶端整合（WebSocket client + 未讀徽章）`frontend/src/app/core/notifications/`
- [ ] T032 [US2] quickstart 討論串/通知測試指南 `specs/001-create-media-journal/quickstart.md`

---

## Phase 5: User Story 3 - 專業用戶追蹤與專注（Priority P3）

**Goal**：追蹤專業評影者、設定通知頻率、顯示加權評分與徽章。
**Independent Test**：追蹤專業帳號並驗證通知 + 專注頁濾出（T038/T039 驗證）。

### Tests
- [ ] T033 [P] [US3] `/api/focus` 與 `/api/notifications` 合約測試 `backend/src/test/java/com/example/mediajournal/focus/FocusApiTest.java`
- [ ] T034 [P] [US3] 前端 e2e（專注操作 + 通知 + 徽章）`frontend/tests/e2e/focus-mode.spec.ts`

### Implementation
- [ ] T035 [P] [US3] 建立 FocusSubscription/Notification/Badge migrations `backend/src/main/resources/db/migration/V3__focus_notifications.sql`
- [ ] T036 [US3] 實作 `FocusService`（追蹤/通知頻率/weighted score 查詢）`backend/src/main/java/com/example/mediajournal/focus/FocusService.java`
- [ ] T037 [US3] 實作 `/api/focus` + `/api/notifications` Controller `backend/src/main/java/com/example/mediajournal/focus/FocusController.java`
- [ ] T038 [US3] 建立專業推薦演算法（根據發文量/互動率）`backend/src/main/java/com/example/mediajournal/profile/ProfessionalRecommendationJob.java`
- [ ] T039 [US3] 建立管理員審核 API 與介面（批准/撤銷專業徽章）`backend/src/main/java/com/example/mediajournal/admin/BadgeApprovalController.java`, `frontend/src/app/features/admin/badge-approval/`
- [ ] T040 [US3] 建立 Angular `focus-hub` module + dashboard `frontend/src/app/features/focus-hub/`
- [ ] T041 [US3] 背景通知排程與 SLA 補償（未送達重試）`backend/src/main/java/com/example/mediajournal/notifications/NotificationDispatcher.java`
- [ ] T042 [US3] quickstart 更新（專注/通知/徽章驗證流程）`specs/001-create-media-journal/quickstart.md`

---

## Phase 6: User Story 4 - 高價值台詞牆（對應 FR-010，Priority P2）

**Goal**：聚合社群收藏最多的引用，提供入口與討論連結。
**Independent Test**：收藏引用後於台詞牆即時顯示，並可導回原討論。

### Tests
- [ ] T043 [P] [US4] `/api/quotes/highlights` 合約測試 `backend/src/test/java/com/example/mediajournal/quotes/QuoteWallApiTest.java`
- [ ] T044 [P] [US4] 前端 e2e（收藏引用 → 台詞牆展示）`frontend/tests/e2e/quote-wall.spec.ts`

### Implementation
- [ ] T045 [P] [US4] 建立 QuoteFavorite/QuoteAggregate migrations `backend/src/main/resources/db/migration/V4__quote_wall.sql`
- [ ] T046 [US4] 實作 `QuoteWallService`（彙整收藏、排序、來源連結）`backend/src/main/java/com/example/mediajournal/quotes/QuoteWallService.java`
- [ ] T047 [US4] 實作 `/api/quotes/highlights` Controller `backend/src/main/java/com/example/mediajournal/quotes/QuoteWallController.java`
- [ ] T048 [P] [US4] 建立 Angular `quote-wall` module（卡片牆 + 討論入口）`frontend/src/app/features/quote-wall/`
- [ ] T049 [US4] 整合引用收藏 UI（entry 詳細頁加入收藏按鈕）`frontend/src/app/features/entry-editor/components/quote-highlight-list.component.ts`
- [ ] T050 [US4] quickstart 加入台詞牆驗證步驟 `specs/001-create-media-journal/quickstart.md`

---

## Phase 7: Polish & Cross-Cutting（Edge cases / 指標）

- [ ] T051 [P] 建立作品版本去重邏輯與提示 `backend/src/main/java/com/example/mediajournal/works/WorkMergeService.java`
- [ ] T052 [P] 台詞截斷預覽與原文附件上傳 `frontend/src/app/shared/components/quote-preview.component.ts`
- [ ] T053 檢舉暫停/24h SLA 追蹤介面 `frontend/src/app/features/admin/moderation/moderation-dashboard.component.ts`
- [ ] T054 專注清單上限提示與整理工具 `frontend/src/app/features/focus-hub/components/focus-limit-banner.component.ts`
- [ ] T055 整合性能測試腳本（篩選 API、通知輪詢）`infrastructure/perf/locustfile.py`
- [ ] T056 [P] 建立通知 SLA 報表/儀表板 `backend/src/main/java/com/example/mediajournal/notifications/NotificationMetricsExporter.java`
- [ ] T057 [P] 更新 quickstart + README（含 `/speckit.*` 流程與量測）`docs/README.md`, `specs/001-create-media-journal/quickstart.md`
- [ ] T058 [P] 全專案驗證（`mvn verify`, `pnpm lint`, `pnpm test`, Playwright）並將結果寫入 `specs/001-create-media-journal/tasks.md` checklist 區
- [ ] T059 建立社群互動率資料管線（紀錄討論/回覆/收藏事件於 `engagement_events` 表）`backend/src/main/java/com/example/mediajournal/analytics/EngagementIngestor.java`
- [ ] T060 [P] 建立互動率儀表板與報表（計算 30 天互動率並對應 SC-004）`frontend/src/app/features/admin/analytics/engagement-dashboard.component.ts`

---

## Dependencies & Execution Order

1. Phase 1 → Phase 2 為所有故事前置。  
2. Phase 3（US1）為 MVP，完成後可獨立交付。  
3. Phase 4（US2）與 Phase 5（US3）可在共用元件完成後平行進行。  
4. Phase 6 依賴 US1 的引用資料與 US2 的討論串連結。  
5. Phase 7 收尾 Edge cases 與指標，需在前述功能完成後執行。

## Parallel Execution Examples

- Phase 2 中 T008/T009/T010/T011 可由不同工程師並行。  
- US1 中 T015 與 T018 可並行；完成後再串接 T016/T017/T019。  
- US2 後端 T026~T028 與前端 T029~T031 可兩組平行。  
- US3 中資料層 T035/T036 與前端 T040 同步進行，T038/T039 另由 Admin 團隊處理。  
- US4 的後端聚合 T045/T046 與前端牆面 T048/T049 可平行完成。

## Implementation Strategy

1. **MVP**：完成 Phase 1-3 即可交付個人觀影筆記（含統計/可見性）。  
2. **Incremental**：依序將 US2 → US3 → US4 合併，每次皆能獨立 demo。  
3. **Quality Gate**：Phase 7 任務確保 Edge cases、性能測試與覆蓋率報告均達成。

---

## Task Totals & Coverage

 - **Total tasks**: 60  
- **US1 tasks**: 11（含 2 測試）  
- **US2 tasks**: 9（含 2 測試）  
- **US3 tasks**: 11（含 2 測試）  
- **US4 tasks**: 7（含 2 測試）  
- **Parallel opportunities**: 18 個 `[P]` 任務  
- **MVP 範圍**: T001~T023

所有任務均符合 `- [ ] T### [P?] [US?] 描述（含檔案路徑）` 格式，並覆蓋 spec/plan 內的全部功能、指標與 Edge cases。
