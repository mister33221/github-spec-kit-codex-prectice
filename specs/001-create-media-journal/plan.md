# Implementation Plan: 影視觀影紀錄社群平台

**Branch**: `001-create-media-journal` | **Date**: 2025-11-15 | **Spec**: `specs/001-create-media-journal/spec.md`  
**Input**: Feature specification from `/specs/001-create-media-journal/spec.md`

**Note**: 執行任何 speckit 工作時，必須同步在 https://github.com/users/mister33221/projects/3 建立或更新對應卡片，並依進度移動至正確泳道。

## Summary

打造一個結合觀影筆記、金句收藏、標籤討論與專業追蹤的暗色系平台。後端採 Spring Boot + PostgreSQL，前端採 Angular + Tailwind，透過 RESTful API 與 OpenAPI 合約協作，並以 JUnit/Vitest/Playwright 維持至少 80% 測試覆蓋率。Platform 將提供個人觀影履歷、標籤討論串、專注通知及高價值台詞牆，並遵循 Project board traceability 原則。

## Technical Context

**Language/Version**: Java 21（Spring Boot 3.x）、TypeScript 5.x（Angular 18）  
**Primary Dependencies**: Spring Boot Web/Data/Validation/Security、MapStruct、Flyway、Angular、Tailwind CSS、NgRx、RxJS、Vitest、Playwright  
**Storage**: PostgreSQL 16（正式）、H2 + Testcontainers（開發/測試）  
**Testing**: JUnit 5 + Spring Test + Testcontainers；Vitest（unit）、Playwright（e2e）  
**Target Platform**: Docker 化 Spring Boot（Linux）+ 現代瀏覽器  
**Project Type**: 前後端分離 Web 應用  
**Performance Goals**: 複合篩選 API p95 < 1s；通知派送延遲 < 6h；輪詢 ≤ 5s  
**Constraints**: 深色/高對比/亮色主題、RESTful 命名、JWT+RBAC、安全審核紀錄、站內通知限定  
**Scale/Scope**: 初期 10k 使用者、每日 5k 筆紀錄、50 個並行討論串、專注清單上限 100

## Constitution Check

- ✅ **Clean Code / SOLID / DDD**：Viewing Journal 為 bounded context，採分層與 DTO/Entity 分離。  
- ✅ **後端 80% 覆蓋率**：quickstart 與任務要求執行 `mvn verify`+JaCoCo，Phase 7 再檢查報告。  
- ✅ **設計系統/前端元件化**：Angular component + Tailwind tokens，提供深色/高對比/亮色主題。  
- ✅ **安全/稽核**：JWT+Refresh token、RBAC、審核紀錄保留與 TLS 配置。  
- ✅ **容器化/RESTful**：Docker Compose、profiles 區分 dev/test/prod，OpenAPI 合約驅動。  
- ✅ **Git/Workflow**：維持 Conventional Commits 與 `/speckit.*` 流程。  
- ✅ **Project Board Traceability**：每個 speckit 任務均於 Project board 建卡或更新狀態。  

## Project Structure

### Documentation

```text
specs/001-create-media-journal/
├── plan.md
├── research.md
├── data-model.md
├── quickstart.md
├── contracts/
└── tasks.md
```

### Source Code

```text
backend/
├── src/main/java/com/example/mediajournal/
│   ├── config/
│   ├── auth/
│   ├── entries/
│   ├── discussions/
│   ├── focus/
│   ├── notifications/
│   └── shared/
├── src/main/resources/
│   ├── application.yml
│   └── db/migration/
└── src/test/java/com/example/mediajournal/
    ├── unit/
    └── integration/

frontend/
├── src/app/
│   ├── core/
│   ├── shared/components/
│   └── features/
│       ├── entry-editor/
│       ├── discussion-board/
│       ├── focus-hub/
│       └── quote-wall/
├── src/styles/
└── tests/

infrastructure/
├── docker-compose.yml
└── k8s/
```

**Structure Decision**：採前後端分離並依 bounded context/功能劃分資料夾，Infrastructure 集中容器與部署設定。

## Complexity Tracking

目前無額外複雜度豁免需求。若未來需要新增 context 或跨專案架構，再於此表記錄。
