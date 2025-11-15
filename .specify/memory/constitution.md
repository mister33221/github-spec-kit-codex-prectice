<!--
Sync Impact Report
- Version change: 1.1.0 -> 1.2.0
- Modified principles: Clean Code wording normalized; Development Workflow expanded
- Added sections: Core Principle 19 (Project Board Traceability)
- Removed sections: None
- Templates requiring updates: None (plan/spec/tasks templates already aligned with new traceability rule)
- Follow-up TODOs: None
-->

# Spec Kit Codex Practice Constitution

## Core Principles

1. **Clean Code**：所有程式碼必須保持可讀、可維護，避免不必要複雜度並持續償還技術債。
2. **後端 80% 覆蓋率**：Spring Boot 後端的自動化測試覆蓋率必須維持在 80% 以上，並於 CI 驗證。
3. **設計系統一致性**：所有 UI 與 API 介面需遵循公司 Design System，善用既有元件與色票。
4. **安全治理**：輸入驗證、密鑰管理、部署流程必須符合公司安全規範並保留稽核紀錄。
5. **強制 Code Review**：任何變更合併至 `main` 前需至少一位合格審查者批准。
6. **Git 與 Conventional Commits**：所有提交遵守 Git 流程並採 Conventional Commits 前綴。
7. **DDD 後端模型**：後端以 Domain-Driven Design 建構 bounded context，禁止跨界耦合。
8. **前端元件化**：Angular 介面以元件、Service、Directive 拆分並保持測試覆蓋。
9. **充分註解與文件**：公開 API、模組與複雜演算法必須具備註解及 README/ADR/Wiki 說明。
10. **遵守 SOLID**：透過介面隔離與依賴反轉維持擴充性與可測性。
11. **自動化品質掃描**：以 SonarQube、ESLint、Detekt 等工具持續檢查程式品質。
12. **RESTful API**：HTTP 端點需符合 RESTful 命名、狀態碼與一致的錯誤結構。
13. **敏捷節奏**：需求、實作、驗證遵循雙週迭代並可隨時交付可用增量。
14. **容器化部署**：前後端與支援元件需提供 Dockerfile、健康檢查與環境設定。
15. **正式前端技術棧**：以 Angular + Tailwind CSS 為唯一正式前端堆疊。
16. **正式後端技術棧**：以 Spring Boot 為唯一後端框架，profiles 區分 dev/test/prod。
17. **資料庫策略**：開發/測試可用 H2，正式環境使用 PostgreSQL 並以 Flyway/Liquibase 管理。
18. **原則優先**：若與其他規範衝突，以本憲章為最高優先，例外需技術負責人核准。
19. **Project Board Traceability**：所有 speckit 工作必須建立或更新 GitHub Project（https://github.com/users/mister33221/projects/3）的卡片，卡片需記錄任務需求、使用對應標籤，並依進度移動至正確泳道。

## Additional Constraints

- **壓力測試門檻**：每次發布前需完成壓測，吞吐量 ≥ 50 req/s 且 p95 延遲 < 300 ms。
- **變更佐證**：Pull Request 必須附上測試結果、設計系統聯動影響、容器執行紀錄。
- **安全配置**：API 金鑰與密碼必須存於受管控的祕密管理服務，所有流量採用 TLS。

## Development Workflow

1. 維護敏捷流程待辦，並以 `/speckit.constitution` → `/speckit.specify` → `/speckit.plan` → `/speckit.tasks` 建立完整脈絡。
2. 實作前須建立或更新 GitHub Project 卡片，於卡片中填寫任務敘述、標籤與預期輸出；狀態更新時同步移動卡片。
3. 每個變更需附帶自動化測試、單元測試、容器驗證與壓測結果，並在 PR 中揭露。
4. 交付後更新 `.specify/memory/checklist.md`（若存在）與 quickstart，以確保下一次 speckit 迭代可直接接手。

## Governance

- 本憲章需由技術負責人 ratify 後方可生效，任何修改需提出版本更新與變更摘要並獲核准。
- Code Review 必須確認所有變更符合憲章條款，若有例外需於 PR 中明示並附技術負責人核可。

**Version**: 1.2.0 | **Ratified**: 2025-11-15 | **Last Amended**: 2025-11-15
