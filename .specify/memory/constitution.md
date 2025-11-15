# Spec Kit Codex Practice Constitution

## Core Principles

1. **Clean Code**：程式碼需保持易讀、低耦合、無多餘邏輯，並持續重構以移除技術債。
2. **後端 80% 覆蓋率**：Spring Boot 後端的單元測試覆蓋率必須長期維持在 80% 以上，並納入 CI 報告。
3. **遵守設計系統**：前端與 API 呈現需符合公司內部設計系統，優先重用既有元件、色彩與排版規則。
4. **安全政策優先**：所有輸入、金鑰、部署與監控流程必須遵照公司安全政策，包含權限控管與稽核要求。
5. **強制 Code Review**：程式碼在合併至 `main` 前必須完成至少一位同儕的 Code Review，並記錄審查重點。
6. **Git 與 Conventional Commits**：一切版本控管使用 Git，Commit 訊息遵守 Conventional Commits 格式並敘明對應需求。
7. **DDD 後端架構**：後端以 Domain-Driven Design 為基礎，界定 bounded context、聚合根與領域事件，禁止違反邊界的耦合。
8. **前端元件化**：前端開發採元件化模式，Angular 元件、Service、Directive 需可重複利用並通過 Story/Unit 測試。
9. **註解與文件**：關鍵模組、演算法與公開 API 需附有適當註解與文件（README、ADR 或內部 Wiki）。
10. **遵守 SOLID**：所有程式碼遵循 SOLID 原則，必要時以介面隔離與依賴反轉維持擴充彈性。
11. **靜態程式碼分析**：所有專案需通過核准的靜態分析工具（ESLint、SonarQube、Detekt 等），禁止忽略高嚴重度警示。
12. **RESTful API**：HTTP 端點必須遵循 RESTful 命名與語意，回傳正確狀態碼並維持一致的錯誤格式。
13. **敏捷流程**：需求、實作與回顧遵循敏捷開發節奏，透過短週期迭代持續交付可驗證增量。
14. **可容器化**：前後端均需提供可運行的容器映像（Dockerfile、健康檢查、環境變數設定），以利測試與部署。
15. **前端技術棧**：正式技術棧限定為 Angular + Tailwind CSS，Tailwind 主題需透過 Design Token 延伸公司樣式。
16. **後端技術棧**：後端使用 Spring Boot，並以 Profiles 區分 dev/test/prod 設定。
17. **資料庫策略**：開發初期可採 H2 內存資料庫進行測試，但最終必須使用 PostgreSQL，並提供遷移腳本（Flyway/Liquibase）。
18. **最高優先權**：所有其他文件與流程不得與本憲章衝突；若衝突，以本憲章為準。

## Additional Constraints

- **壓力測試門檻**：每次發版前需完成壓力測試，吞吐量（throughput）需達 50 req/s 以上，平均回應時間（average response time）必須低於 300 ms。
- **品質佐證**：每個 Pull Request 必須附上單元測試與靜態分析結果、必要的設計系統截圖或錄影，以及容器啟動成功紀錄。
- **安全配置**：API 金鑰、密碼與敏感設定須存放在核准的秘密管理服務中並留存審計紀錄，所有敏感流量一律啟用 TLS。

## Development Workflow

1. 透過敏捷流程維護需求待辦，並於每次工作前以 `/speckit.constitution`、`/speckit.specify`、`/speckit.plan`、`/speckit.tasks` 更新最新上下文。
2. 實作時先撰寫或更新測試，再完成功能開發，最後執行靜態分析、單元測試與壓力測試（含容器啟動驗證）。
3. 建立 Pull Request 時，需列出使用的 `/speckit.*` 指令、已更新的檔案（spec、plan、checklist 等）與符合本憲章的檢查結果。

## Governance

- 本憲章對所有程式碼、流程與工具具有最高效力，修改憲章需經技術負責人審查並更新版本與 Ratification 日期。
- Code Review 必須確認變更符合所有條款；若有例外情形，需在 PR 中說明補救方案與期限。

**Version**: 1.1.0 | **Ratified**: 2025-11-15 | **Last Amended**: 2025-11-15
