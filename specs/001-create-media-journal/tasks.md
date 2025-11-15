# Tasks: 影視觀影紀錄社群平台

**Input**: Design documents from `/specs/001-create-media-journal/`  
**Prerequisites**: plan.md, spec.md, research.md, data-model.md, contracts/

**Tests**: 依需求於各 User Story 加入必要的整合或 e2e 測試。  
**Organization**: 任務依階段與 User Story 拆分，可獨立實作與驗證。

## Phase 1: Setup（共同基礎）

- [ ] T000 建立或更新 GitHub Project 卡片（描述 feature、設定標籤與泳道 Todo）
- [X] T001 建立 Spring Boot 專案骨架 `backend/src/main/java/com/example/mediajournal`
- [X] T002 初始化 Angular 工作區並安裝 Tailwind/NgRx/Vitest `frontend/`
- [X] T003 [P] 建立 `infrastructure/docker-compose.yml`（app + postgres + frontend）與 `.env.example`
- [X] T004 [P] 設定 lint/formatter/git hooks（`backend/pom.xml`, `frontend/package.json`, `.husky/`）

---

## Phase 2: Foundational（阻塞前提）

- [X] T005 建立 Flyway 基礎遷移 `backend/src/main/resources/db/migration/V1__baseline.sql`
- [X] T006 建立共用 domain/repository layer 並串接 PostgreSQL/H2 profiles
- [X] T007 實作 JWT + RBAC + 審核稽核記錄 `backend/src/main/java/com/example/mediajournal/config/SecurityConfig.java`
- [X] T008 [P] 建立全域錯誤處理與 API 版本設定 `backend/src/main/java/com/example/mediajournal/config/WebConfig.java`
- [X] T009 [P] 建立 Angular core 模組（API client、auth guard、theme service）`frontend/src/app/core/`
- [X] T010 [P] 建立 Tailwind design tokens 與深色/高對比/亮色主題 `frontend/src/styles/tailwind.config.ts`
- [X] T011 建立觀測性基礎（Micrometer + OpenTelemetry + 通知 SLA 指標）`backend/src/main/java/com/example/mediajournal/config/ObservabilityConfig.java`
- [X] T012 設定 JaCoCo/ESLint 報告門檻，CI 需檢查 80% 覆蓋率

Checkpoint：完成後可進入 User Story 階段，並在 Project board 將卡片移至「In Progress」。

---

## Phase 3: User Story 1 - 建立個人觀影筆記（Priority P1，MVP）

**Goal**：新增/編輯觀影紀錄、標籤、引用金句並更新個人版面。  
**Independent Test**：於 2 分鐘內完成新增/編輯紀錄並於個人版面看到摘要。

### Tests

- [ ] T013 [P] [US1] `/api/entries` 合約/整合測試 `backend/src/test/java/com/example/mediajournal/entries/EntriesApiTest.java`
- [ ] T014 [P] [US1] 前端 e2e（建立/編輯紀錄流程）`frontend/tests/e2e/entry-flow.spec.ts`

### Implementation

- [ ] T015 [P] [US1] 建立 ViewingEntry/QuoteHighlight/Tag Entities 與 repositories
- [ ] T016 [US1] 實作 `ViewingEntryService`（引用長度、標籤批次）`backend/.../entries/ViewingEntryService.java`
- [ ] T017 [US1] 實作 `/api/entries` Controller（GET/POST/PATCH）
- [ ] T018 [P] [US1] 建立 Angular `entry-editor` module `frontend/src/app/features/entry-editor/`
- [ ] T019 [US1] 建立 entry timeline component 與統計圖卡
- [ ] T020 [US1] 實作 profile 統計/可見性 API `backend/src/main/java/com/example/mediajournal/profile/ProfileController.java`
- [ ] T021 [US1] 建立 `ProfileSummaryService`（平均評分、常用標籤、可見性設定）
- [ ] T022 [US1] 前端個人版面設定頁（公開/半公開/私人）
- [ ] T023 [US1] 更新 quickstart（新增/編輯紀錄與可見性驗證）

Checkpoint：完成 MVP，並在 Project board 上建立/更新「US1 完成」註記。

---

## Phase 4: User Story 2 - 標籤討論與即時回覆（Priority P2）

**Goal**：允許用戶在作品或標籤下開啟討論串並即時互動。  
**Independent Test**：兩個帳號在同標籤互動且看到立即更新。

### Tests

- [ ] T024 [P] [US2] `/api/discussions` + `/api/discussions/{id}/replies` 合約測試
- [ ] T025 [P] [US2] 前端 e2e（標籤討論、引用回覆、即時更新）

### Implementation

- [ ] T026 [P] [US2] 建立 DiscussionThread/Reply/ModerationCase migrations
- [ ] T027 [US2] 實作 `DiscussionService`（層級回覆、引用文字）
- [ ] T028 [US2] 實作討論 API + SSE/WebSocket 更新 `backend/.../DiscussionsController.java`
- [ ] T029 [P] [US2] 建立 Angular `discussion-board` module
- [ ] T030 [US2] 標籤聚合牆與引用 UI `frontend/.../tag-thread-view.component.ts`
- [ ] T031 [US2] 通知客戶端整合（WebSocket client + 未讀徽章）
- [ ] T032 [US2] 更新 quickstart：討論串/通知測試指南

---

## Phase 5: User Story 3 - 專業用戶追蹤與專注（Priority P3）

**Goal**：追蹤專業評影者、設定通知頻率、顯示加權評分與專業徽章。  
**Independent Test**：追蹤專業帳號並在新紀錄 6 小時內收到站內通知。

### Tests

- [ ] T033 [P] [US3] `/api/focus` 與 `/api/notifications` 合約測試
- [ ] T034 [P] [US3] 前端 e2e（專注操作 + 通知 + 徽章）

### Implementation

- [ ] T035 [P] [US3] 建立 FocusSubscription/Notification/Badge migrations
- [ ] T036 [US3] 實作 `FocusService`（追蹤、通知頻率、weighted score 查詢）
- [ ] T037 [US3] 實作 `/api/focus` + `/api/notifications` Controller
- [ ] T038 [US3] 建立專業推薦演算法 `backend/.../ProfessionalRecommendationJob.java`
- [ ] T039 [US3] 建立管理員審核 API 與前端介面 `frontend/src/app/features/admin/badge-approval/`
- [ ] T040 [US3] 建立 Angular `focus-hub` module + dashboard
- [ ] T041 [US3] 背景通知排程與 SLA 補償 `backend/.../NotificationDispatcher.java`
- [ ] T042 [US3] 更新 quickstart：專注/通知/徽章驗證流程

---

## Phase 6: User Story 4 - 高價值台詞牆（Priority P2）

**Goal**：聚合社群收藏最多的引用，提供回到原紀錄/討論入口。  
**Independent Test**：收藏台詞後能在台詞牆看到該引用並導回來源。

### Tests

- [ ] T043 [P] [US4] `/api/quotes/highlights` 合約測試
- [ ] T044 [P] [US4] 前端 e2e（收藏引用 → 台詞牆展示）

### Implementation

- [ ] T045 [P] [US4] 建立 QuoteFavorite/QuoteAggregate migrations
- [ ] T046 [US4] 實作 `QuoteWallService`（彙整收藏、排序、來源連結）
- [ ] T047 [US4] 實作 `/api/quotes/highlights` Controller
- [ ] T048 [P] [US4] 建立 Angular `quote-wall` module（卡片牆 + 討論入口）
- [ ] T049 [US4] 引用收藏 UI（entry 詳細頁收藏按鈕）`frontend/.../quote-highlight-list.component.ts`
- [ ] T050 [US4] 更新 quickstart：台詞牆驗證步驟

---

## Phase 7: Polish & Cross-Cutting（Edge cases / 指標 / Traceability）

- [ ] T051 [P] 建立作品版本去重服務 `backend/src/main/java/com/example/mediajournal/works/WorkMergeService.java`
- [ ] T052 [P] 台詞截斷預覽與附件流程 `frontend/src/app/shared/components/quote-preview.component.ts`
- [ ] T053 檢舉暫停/24h SLA 追蹤介面 `frontend/src/app/features/admin/moderation/moderation-dashboard.component.ts`
- [ ] T054 專注清單上限提示與整理工具 `frontend/src/app/features/focus-hub/components/focus-limit-banner.component.ts`
- [ ] T055 建立性能測試腳本並記錄結果 `infrastructure/perf/locustfile.py`
- [ ] T056 [P] 建立通知 SLA 報表/儀表板 `backend/.../notifications/NotificationMetricsExporter.java`
- [ ] T057 [P] 更新 quickstart + README（含 `/speckit.*` 流程與量測）`docs/README.md`, `specs/.../quickstart.md`
- [ ] T058 [P] 全專案驗證（`mvn verify`, `pnpm lint`, `pnpm test`, Playwright）並寫入 tasks checklist
- [ ] T059 建立互動率資料管線（`engagement_events` 表與 ingestor）`backend/.../analytics/EngagementIngestor.java`
- [ ] T060 [P] 建立互動率儀表板與報表 `frontend/src/app/features/admin/analytics/engagement-dashboard.component.ts`
- [ ] T061 壓測與通知 SLA 結果彙整，附報告連結並更新 Project 卡片狀態
- [ ] T062 記錄 SC-004 互動率達成情形（更新 quickstart、Project 卡片與 README 附錄）

---

## Dependencies & Execution Order

1. Phase 1 → Phase 2 為所有任務前置。  
2. Phase 3（US1）提供 MVP，完成後可在 Project board 更新卡片並 demo。  
3. Phase 4（US2）與 Phase 5（US3）可在基礎完成後平行進行。  
4. Phase 6 依賴 US1/US2 產出的資料。  
5. Phase 7 收尾 Edge cases、觀測性、壓測與 traceability 流程。

## Parallel Execution Examples

- Phase 2 中 T008/T009/T010/T011 可平行。  
- US1 中 T015 與 T018 可並行，再串接 T016/T017/T019。  
- US2 後端 T026~T028 與前端 T029~T031 可分組進行。  
- US3 的 T035/T036 與前端 T040 可同步，T038/T039 由 admin 團隊處理。  
- US4 的 T045/T046 與 T048/T049 可分工。  
- Phase 7 中 T055、T056、T059、T060、T061/T062 可交錯實作。

## Implementation Strategy

1. **MVP**：完成 Phase 1-3 即可交付個人觀影筆記與統計。  
2. **Incremental**：依序合併 US2 → US3 → US4，每次皆可獨立 demo。  
3. **Quality Gate**：Phase 7 任務確保 Edge cases、性能測試、互動率報表與 Project board traceability 都完成。

---

## Task Totals & Coverage

- **Total tasks**: 63  
- **US1 tasks**: 11（含 2 測試）  
- **US2 tasks**: 9（含 2 測試）  
- **US3 tasks**: 10（含 2 測試與推薦/審核）  
- **US4 tasks**: 8（含 2 測試）  
- **Parallel opportunities**: 18 個 `[P]` 任務  
- **MVP 範圍**: T000~T023

所有任務均符合 `- [ ] T### [P?] [US?] 描述` 格式，且涵蓋 spec/plan 需求、Project board traceability 與壓測佐證流程。
