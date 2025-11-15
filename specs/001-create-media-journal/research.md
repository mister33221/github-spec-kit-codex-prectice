# research.md

## 決策 1：深色 / 高對比 / 亮色主題切換
- **結論**：採用 Tailwind CSS + 自訂 Design Tokens，並建立 Theme Service 於 Angular。切換僅動態替換 data-theme 與 CSS 變數。
- **理由**：Tailwind 支援 data-theme 條件 class，可保證 AA 對比計算；同時不需引入額外 UI framework，維持輕量。
- **替代方案**：
  - 使用第三方 UI Kit（例如 Material 3）：與公司設計系統不符、客製成本高。
  - 純 CSS variables 手寫：缺乏 Tailwind 原子化便利性，容易重複定義。

## 決策 2：站內通知派送
- **結論**：後端建立 
otification 表記錄「專注」事件，透過排程掃描未讀並推送至前端 WebSocket 通道；若離線則於下次登入拉取。
- **理由**：僅需站內通知即可滿足需求，WebSocket/Server-Sent Events 配合作用能在 5 秒內推播，符合 SLA。
- **替代方案**：
  - Email/Push API：需求不需，且會增加外部整合與法遵負擔。
  - 純輪詢 API：延遲較高且耗頻寬，難達即時感。

## 決策 3：綜合評分權重
- **結論**：資料庫層建 ggregate_scores 檢視，依「專業用戶：一般用戶 = 2:1」權重平均；亦保留 timestamp 以供趨勢分析。
- **理由**：在 SQL 中集中計算可避免多端邏輯不一致，並易於依條件（作品/年份）聚合；PostgreSQL CTE 可高效處理。
- **替代方案**：
  - 於應用層計算：每次查詢需載入大量紀錄，難守 1 秒 SLA。
  - 事先寫入實體欄位：更新成本高，需多筆 transaction。

## 決策 4：檢舉審核流程
- **結論**：建立 moderation_case 表格追蹤每筆檢舉，狀態機為 PENDING -> UNDER_REVIEW -> RESOLVED/REJECTED，並於 24h SLA 內由後台工具處理。
- **理由**：可保存審核紀錄供稽核，並支援只下架單一回覆的需求。
- **替代方案**：
  - 直接刪除回覆：破壞證據，違反憲章安全條款。
  - 以第三方審核服務：超出 MVP 範圍且無資料主權控制。
