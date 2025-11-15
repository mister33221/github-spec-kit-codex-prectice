# Spec Kit Codex Practice Constitution

## Core Principles

1. **Clean Code**：程式碼需保持易讀、低耦合、無多餘邏輯，並持續重構以移除技術債。
2. **後端 80% 覆蓋率**：Spring Boot 後端單元測試覆蓋率必須維持在 80% 以上，並於 CI 報告公開。
3. **遵守設計系統**：前端 UI 與 API 呈現需符合公司設計系統，優先重用既有元件、色票與版型。
4. **安全政策優先**：輸入驗證、憑證管理、部署與監控流程需遵照公司安全政策，並保留稽核紀錄。
5. **強制 Code Review**：任何變更在合併至 `main` 前必須完成至少一位同儕的 Code Review，記錄審查重點。
6. **Git 與 Conventional Commits**：所有版本控管使用 Git，Commit 訊息遵循 Conventional Commits 並敘明需求或任務代碼。
7. **DDD 後端架構**：後端以 Domain-Driven Design 為基礎，明確界定 bounded context、聚合根與領域事件，禁止跨界耦合。
8. **前端元件化**：Angular 介面採元件化開發，Service、Directive 與 Component 需可重複使用並具備測試覆蓋。
9. **註解與文件**：關鍵模組、演算法與公開 API 必須提供註解與文件（README、ADR 或內部 Wiki）。
10. **遵守 SOLID**：程式必須符合 SOLID 原則，透過介面隔離與依賴反轉維持擴充性。
11. **靜態程式碼分析**：全專案需通過核准的靜態分析工具（SonarQube、ESLint、Detekt 等），不得忽視高嚴重度警示。
12. **RESTful API**：HTTP 端點需遵守 RESTful 命名與語意（名詞路由、正確動詞、標準狀態碼、統一錯誤格式）。
13. **敏捷開發**：需求、實作與回顧遵循敏捷節奏，透過短週期迭代交付可驗證成果。
14. **可容器化**：前後端需提供可運行的容器映像（Dockerfile、健康檢查、環境變數設定）以利測試與部署。
15. **前端技術棧**：正式技術棧為 Angular + Tailwind CSS，Tailwind 主題透過 Design Token 延伸公司樣式。
16. **後端技術棧**：後端使用 Spring Boot，並以 Profiles 區分 dev/test/prod 設定與密鑰。
17. **資料庫策略**：開發初期可使用 H2 進行測試，但最終需切換至 PostgreSQL，並提供 Flyway/Liquibase 遷移腳本。
18. **最高優先權**：若與其他規範衝突，以本憲章為最高準則，任何例外需經技術負責人核准。

## Additional Constraints

- **壓力測試門檻**：每次發版前需完成壓力測試，吞吐量（throughput）需達 50 req/s 以上，平均回應時間需低於 300 ms。
- **品質佐證**：所有 Pull Request 必須附上單元測試與靜態分析結果、必要的設計系統截圖或錄影，以及容器啟動成功紀錄。
- **安全配置**：API 金鑰、密碼與敏感設定須存放於核准的秘密管理服務並保留審計紀錄，敏感流量一律啟用 TLS。

## Development Workflow

1. 透過敏捷流程維護待辦事項，並於每次工作前使用 `/speckit.constitution`、`/speckit.specify`、`/speckit.plan`、`/speckit.tasks` 同步上下文。
2. 實作時先撰寫或更新測試，再開發功能，最後執行靜態分析、單元測試、容器啟動驗證與壓力測試。
3. 建立 Pull Request 時，需列出使用的 `/speckit.*` 指令、更新過的文件（spec、plan、checklist 等），並勾稽符合憲章的檢查結果。

## Governance

- 本憲章對所有程式碼與流程具有最高效力；修訂需經技術負責人核准並更新版本與 Ratification 日期。
- Code Review 必須確認變更符合憲章條款；任何例外需在 PR 中描述補救方案與時程。

**Version**: 1.1.0 | **Ratified**: 2025-11-15 | **Last Amended**: 2025-11-15
