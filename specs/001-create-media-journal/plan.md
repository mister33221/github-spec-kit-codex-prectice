# Implementation Plan: 影視觀影紀錄社群平台

**Branch**: `001-create-media-journal` | **Date**: 2025-11-15 | **Spec**: `specs/001-create-media-journal/spec.md`
**Input**: Feature specification from `/specs/001-create-media-journal/spec.md`

**Note**: 全檔使用繁體中文撰寫，並遵循 speckit.plan 指南。

## Summary

建構一個結合觀影筆記、引用金句、標籤討論與專業追蹤的暗色系社群平台。後端以 Spring Boot + PostgreSQL 處理資料與通知，前端以 Angular + Tailwind 呈現，透過 RESTful API 與 OpenAPI 合約協作，並以 JUnit/Vitest/Playwright 提供測試覆蓋以符合 80% 要求。

## Technical Context

**Language/Version**: Java 21（Spring Boot 3.x）、TypeScript 5.x（Angular 18）  
**Primary Dependencies**: Spring Boot Web/Data/Validation/Security、MapStruct、Flyway、Angular、Tailwind CSS、RxJS、NgRx、Vitest、Playwright  
**Storage**: PostgreSQL 16（正式）、H2 + Testcontainers（開發/測試）  
**Testing**: JUnit 5 + Spring Test + Testcontainers、Vitest（unit）、Playwright（e2e）  
**Target Platform**: Docker 化 Spring Boot（Linux）+ 現代瀏覽器  
**Project Type**: 前後端分離 Web 應用  
**Performance Goals**: 複合篩選 API p95 < 1s、通知派送延遲 < 6h、輪詢/即時更新 ≤ 5s  
**Constraints**: 深色 + 高對比 + 亮色主題、RESTful 命名、JWT + RBAC、安全審核紀錄、站內通知限定  
**Scale/Scope**: 初期 10k 使用者、每日 5k 筆紀錄、50 個並行討論串、專注清單上限 100

## Constitution Check

- ✅ **Clean Code / SOLID / DDD**：以 Viewing Journal 為 bounded context，分層架構、DTO/Entity 分離。  
- ✅ **後端 80% 覆蓋率**：quickstart 及任務要求執行 `mvn verify` + JaCoCo，並於 Phase 6 驗證報告。  
- ✅ **設計系統/前端件化**：Angular component + Tailwind tokens，提供深色、高對比、亮色主題。  
- ✅ **安全/稽核**：JWT + Refresh token、RBAC 角色、檢舉流程保留審核紀錄、TLS 及金鑰管控。  
- ✅ **容器化/RESTful**：Docker Compose、profiles 區分 dev/test/prod、OpenAPI 合約驅動。  
- ✅ **Git/Workflow**：維持 Conventional Commits 與 `/speckit.*` 流程記錄。

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

**Structure Decision**：保留前後端分離並依 bounded context/功能劃分資料夾，Infrastructure 集中容器與部署設定，符合憲章對設計系統與容器化要求。

## Complexity Tracking

目前無需額外複雜性豁免。
