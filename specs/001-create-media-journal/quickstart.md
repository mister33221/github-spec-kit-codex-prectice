# quickstart.md

## 先決條件
1. 安裝 uvx、pnpm 9+、Java 21、Docker Desktop。
2. 於專案根目錄執行 uvx --from git+https://github.com/github/spec-kit.git specify init --here（首次）確保 .specify/ 設定存在。
3. 建立 .env：
   `	ext
   POSTGRES_PASSWORD=devpass
   JWT_SECRET=local-secret
   `

## 啟動流程
1. 啟動資料庫 + 服務：
   `powershell
   docker compose up -d postgres
   `
2. 啟動後端：
   `powershell
   cd backend
   mvn spring-boot:run -Dspring-boot.run.profiles=dev
   `
3. 啟動前端：
   `powershell
   cd frontend
   pnpm install
   pnpm dev -- --open
   `
4. 背景通知排程：預設由 Spring @Scheduled 觸發，如需手動執行可呼叫 /internal/notifications/dispatch（受管理員 JWT 保護）。

## 測試與驗證
1. 後端單元＋整合測試：cd backend && mvn verify（自動使用 Testcontainers 啟動 PostgreSQL/H2）。
2. 前端：cd frontend && pnpm test；E2E：pnpm exec playwright test。
3. 合約測試：使用 pnpm exec openapi-coverage 針對 specs/001-create-media-journal/contracts/api.yaml 驗證端點。
4. Lint/格式化：pnpm lint（前端），mvn spotless:apply（後端）。

## 常見問題
- **埠衝突**：後端預設 :8080、前端 :4200，如需修改請同步更新 rontend/src/app/core/api.config.ts。
- **通知未即時更新**：確認前端 WebSocket URL 指向 ws://localhost:8080/ws/notifications，並於瀏覽器允許該連線。
- **Testcontainers 無法啟動**：請確保 Docker Desktop 內的 WSL 整合開啟且磁碟空間足夠。

## API 說明
- 啟動後端後，可於 http://localhost:8080/docs 檢視 Swagger UI，快速瀏覽與測試 API。
- 若需 OpenAPI JSON，路徑為 http://localhost:8080/api/docs，可搭配 pnpm exec openapi-coverage 或其他工具進行合約驗證。

