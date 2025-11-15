# Tasks: 敶梯?閫敶梁??冗蝢文像??
**Input**: Design documents from `/specs/001-create-media-journal/`  
**Prerequisites**: plan.md, spec.md, research.md, data-model.md, contracts/

**Tests**: 靘?瘙??User Story ?敹???? e2e 皜祈岫?? 
**Organization**: 隞餃?靘?畾菔? User Story ??嚗?函?撖虫???霅?
## Phase 1: Setup嚗?蝷?

- [ ] T000 撱箇????GitHub Project ?∠?嚗?餈?feature?身摰?蝐方?瘜喲? Todo嚗?- [ ] T001 撱箇? Spring Boot 撠?撉冽 `backend/src/main/java/com/example/mediajournal`
- [ ] T002 ????Angular 撌乩??銝血?鋆?Tailwind/NgRx/Vitest `frontend/`
- [ ] T003 [P] 撱箇? `infrastructure/docker-compose.yml`嚗pp + postgres + frontend嚗? `.env.example`
- [ ] T004 [P] 閮剖? lint/formatter/git hooks嚗backend/pom.xml`, `frontend/package.json`, `.husky/`嚗?
---

## Phase 2: Foundational嚗憛???

- [ ] T005 撱箇? Flyway ?箇??瑞宏 `backend/src/main/resources/db/migration/V1__baseline.sql`
- [ ] T006 撱箇??梁 domain/repository layer 銝虫葡??PostgreSQL/H2 profiles
- [ ] T007 撖虫? JWT + RBAC + 撖拇蝔賣閮? `backend/src/main/java/com/example/mediajournal/config/SecurityConfig.java`
- [ ] T008 [P] 撱箇??典??航炊????API ?閮剖? `backend/src/main/java/com/example/mediajournal/config/WebConfig.java`
- [ ] T009 [P] 撱箇? Angular core 璅∠?嚗PI client?uth guard?heme service嚗frontend/src/app/core/`
- [ ] T010 [P] 撱箇? Tailwind design tokens ?楛??擃?瘥?鈭株銝駁? `frontend/src/styles/tailwind.config.ts`
- [ ] T011 撱箇?閫皜祆批蝷?Micrometer + OpenTelemetry + ? SLA ??嚗backend/src/main/java/com/example/mediajournal/config/ObservabilityConfig.java`
- [ ] T012 閮剖? JaCoCo/ESLint ?勗??瑼鳴?CI ?瑼Ｘ 80% 閬???
Checkpoint嚗????舫脣 User Story ?挾嚗蒂??Project board 撠?宏?喋n Progress??
---

## Phase 3: User Story 1 - 撱箇??犖閫敶梁?閮?Priority P1嚗VP嚗?
**Goal**嚗憓?蝺刻摩閫敶梁???蝐扎??券??乩蒂?湔?犖??? 
**Independent Test**嚗 2 ???批??憓?蝺刻摩蝝?蒂?澆犖??????
### Tests

- [ ] T013 [P] [US1] `/api/entries` ??/?游?皜祈岫 `backend/src/test/java/com/example/mediajournal/entries/EntriesApiTest.java`
- [ ] T014 [P] [US1] ?垢 e2e嚗遣蝡?蝺刻摩蝝??蝔?`frontend/tests/e2e/entry-flow.spec.ts`

### Implementation

- [ ] T015 [P] [US1] 撱箇? ViewingEntry/QuoteHighlight/Tag Entities ??repositories
- [ ] T016 [US1] 撖虫? `ViewingEntryService`嚗??券摨艾?蝐斗甈∴?`backend/.../entries/ViewingEntryService.java`
- [ ] T017 [US1] 撖虫? `/api/entries` Controller嚗ET/POST/PATCH嚗?- [ ] T018 [P] [US1] 撱箇? Angular `entry-editor` module `frontend/src/app/features/entry-editor/`
- [ ] T019 [US1] 撱箇? entry timeline component ?絞閮???- [ ] T020 [US1] 撖虫? profile 蝯梯?/?航???API `backend/src/main/java/com/example/mediajournal/profile/ProfileController.java`
- [ ] T021 [US1] 撱箇? `ProfileSummaryService`嚗像???虜?冽?蝐扎閬扯身摰?
- [ ] T022 [US1] ?垢?犖?閮剖????祇?/???蝘犖嚗?- [ ] T023 [US1] ?湔 quickstart嚗憓?蝺刻摩蝝???航??折?霅?

Checkpoint嚗???MVP嚗蒂??Project board 銝遣蝡??湔?S1 摰??酉閮?
---

## Phase 4: User Story 2 - 璅惜閮????閬?Priority P2嚗?
**Goal**嚗?閮梁?嗅雿???蝐支???閮?銝脖蒂?單?鈭??? 
**Independent Test**嚗?董???蝐支????蝡?湔??
### Tests

- [ ] T024 [P] [US2] `/api/discussions` + `/api/discussions/{id}/replies` ??皜祈岫
- [ ] T025 [P] [US2] ?垢 e2e嚗?蝐方?隢??典?閬??堆?

### Implementation

- [ ] T026 [P] [US2] 撱箇? DiscussionThread/Reply/ModerationCase migrations
- [ ] T027 [US2] 撖虫? `DiscussionService`嚗惜蝝?閬??冽?摮?
- [ ] T028 [US2] 撖虫?閮? API + SSE/WebSocket ?湔 `backend/.../DiscussionsController.java`
- [ ] T029 [P] [US2] 撱箇? Angular `discussion-board` module
- [ ] T030 [US2] 璅惜????撘 UI `frontend/.../tag-thread-view.component.ts`
- [ ] T031 [US2] ?摰Ｘ蝡舀??WebSocket client + ?芾?敺賜?嚗?- [ ] T032 [US2] ?湔 quickstart嚗?隢葡/?皜祈岫??

---

## Phase 5: User Story 3 - 撠平?冽餈質馱??瘜剁?Priority P3嚗?
**Goal**嚗蕭頩文?璆剛?敶梯身摰?餌??＊蝷箏?甈???撠平敺賜??? 
**Independent Test**嚗蕭頩文?璆剖董?蒂?冽蝝??6 撠??扳?啁??折??
### Tests

- [ ] T033 [P] [US3] `/api/focus` ??`/api/notifications` ??皜祈岫
- [ ] T034 [P] [US3] ?垢 e2e嚗?瘜冽?雿?+ ? + 敺賜?嚗?
### Implementation

- [ ] T035 [P] [US3] 撱箇? FocusSubscription/Notification/Badge migrations
- [ ] T036 [US3] 撖虫? `FocusService`嚗蕭頩扎?餌??eighted score ?亥岷嚗?- [ ] T037 [US3] 撖虫? `/api/focus` + `/api/notifications` Controller
- [ ] T038 [US3] 撱箇?撠平?刻瞍?瘜?`backend/.../ProfessionalRecommendationJob.java`
- [ ] T039 [US3] 撱箇?蝞∠??∪祟??API ??蝡臭???`frontend/src/app/features/admin/badge-approval/`
- [ ] T040 [US3] 撱箇? Angular `focus-hub` module + dashboard
- [ ] T041 [US3] ??????SLA 鋆? `backend/.../NotificationDispatcher.java`
- [ ] T042 [US3] ?湔 quickstart嚗?瘜??/敺賜?撽?瘚?

---

## Phase 6: User Story 4 - 擃?澆閰?嚗riority P2嚗?
**Goal**嚗??冗蝢斗??憭?撘嚗?靘??啣?蝝??閮??亙?? 
**Independent Test**嚗?閰??賢?啗????啗府撘銝血???皞?
### Tests

- [ ] T043 [P] [US4] `/api/quotes/highlights` ??皜祈岫
- [ ] T044 [P] [US4] ?垢 e2e嚗???????啗???蝷綽?

### Implementation

- [ ] T045 [P] [US4] 撱箇? QuoteFavorite/QuoteAggregate migrations
- [ ] T046 [US4] 撖虫? `QuoteWallService`嚗??湔??摨?皞??嚗?- [ ] T047 [US4] 撖虫? `/api/quotes/highlights` Controller
- [ ] T048 [P] [US4] 撱箇? Angular `quote-wall` module嚗?? + 閮??亙嚗?- [ ] T049 [US4] 撘?嗉? UI嚗ntry 閰喟敦?????`frontend/.../quote-highlight-list.component.ts`
- [ ] T050 [US4] ?湔 quickstart嚗閰?撽?甇仿?

---

## Phase 7: Polish & Cross-Cutting嚗dge cases / ?? / Traceability嚗?
- [ ] T051 [P] 撱箇?雿???駁??? `backend/src/main/java/com/example/mediajournal/works/WorkMergeService.java`
- [ ] T052 [P] ?啗??芣?汗??隞嗆?蝔?`frontend/src/app/shared/components/quote-preview.component.ts`
- [ ] T053 瑼Ｚ??怠?/24h SLA 餈質馱隞 `frontend/src/app/features/admin/moderation/moderation-dashboard.component.ts`
- [ ] T054 撠釣皜銝??內??極??`frontend/src/app/features/focus-hub/components/focus-limit-banner.component.ts`
- [ ] T055 撱箇??扯皜祈岫?單銝西?????`infrastructure/perf/locustfile.py`
- [ ] T056 [P] 撱箇?? SLA ?梯”/?銵冽 `backend/.../notifications/NotificationMetricsExporter.java`
- [ ] T057 [P] ?湔 quickstart + README嚗 `/speckit.*` 瘚???皜穿?`docs/README.md`, `specs/.../quickstart.md`
- [ ] T058 [P] ?典?獢?霅?`mvn verify`, `pnpm lint`, `pnpm test`, Playwright嚗蒂撖怠 tasks checklist
- [ ] T059 撱箇?鈭????恣蝺?`engagement_events` 銵刻? ingestor嚗backend/.../analytics/EngagementIngestor.java`
- [ ] T060 [P] 撱箇?鈭???銵冽?銵?`frontend/src/app/features/admin/analytics/engagement-dashboard.component.ts`
- [ ] T061 憯葫? SLA 蝯?敶嚗??勗????銝行??Project ?∠????- [ ] T062 閮? SC-004 鈭?????敶ｇ??湔 quickstart?roject ?∠???README ??嚗?
---

## Dependencies & Execution Order

1. Phase 1 ??Phase 2 ?箸??遙??蝵柴? 
2. Phase 3嚗S1嚗?靘?MVP嚗????臬 Project board ?湔?∠?銝?demo?? 
3. Phase 4嚗S2嚗? Phase 5嚗S3嚗?典蝷???撟唾??脰??? 
4. Phase 6 靘陷 US1/US2 ?Ｗ???? 
5. Phase 7 ?嗅偏 Edge cases??皜祆扼?皜祈? traceability 瘚???
## Parallel Execution Examples

- Phase 2 銝?T008/T009/T010/T011 ?臬像銵? 
- US1 銝?T015 ??T018 ?臭蒂銵??葡??T016/T017/T019?? 
- US2 敺垢 T026~T028 ??蝡?T029~T031 ?臬?蝯脰??? 
- US3 ??T035/T036 ??蝡?T040 ?臬?甇伐?T038/T039 ??admin ?????? 
- US4 ??T045/T046 ??T048/T049 ?臬?撌乓? 
- Phase 7 銝?T055?056?059?060?061/T062 ?臭漱?臬祕雿?
## Implementation Strategy

1. **MVP**嚗???Phase 1-3 ?喳鈭支??犖閫敶梁?閮?蝯梯??? 
2. **Incremental**嚗?摨?雿?US2 ??US3 ??US4嚗?甈∠??舐蝡?demo?? 
3. **Quality Gate**嚗hase 7 隞餃?蝣箔? Edge cases?扯皜祈岫?????梯”??Project board traceability ?賢???
---

## Task Totals & Coverage

- **Total tasks**: 63  
- **US1 tasks**: 11嚗 2 皜祈岫嚗? 
- **US2 tasks**: 9嚗 2 皜祈岫嚗? 
- **US3 tasks**: 10嚗 2 皜祈岫???撖拇嚗? 
- **US4 tasks**: 8嚗 2 皜祈岫嚗? 
- **Parallel opportunities**: 18 ??`[P]` 隞餃?  
- **MVP 蝭?**: T000~T023

??遙??蝚血? `- [ ] T### [P?] [US?] ?膩` ?澆?嚗?瘨菔? spec/plan ?瘙roject board traceability ??皜砌?霅?蝔?
