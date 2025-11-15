# data-model.md

## 實體與欄位

### Work
- id (UUID)：主鍵。
- 	itle (text)
- elease_year (int)
- official_rating (decimal 2)
- genres (string[])
- language (text)
- cover_url (text, nullable)
- ersion_tag (text, optional，用於重映版本識別)
- **關聯**：1:N ViewingEntry、1:N DiscussionThread。

### ViewingEntry
- id (UUID)
- user_id (UUID)
- work_id (UUID, FK)
- watched_at (timestamp)
- score (tinyint 1-10)
- isibility (enum: PUBLIC / FOLLOWERS / PRIVATE)
- eview_body (text)
- 	ags (string[] from controlled Tag)
- **關聯**：1:N QuoteHighlight。
- **規則**：同一 work 每人可有多筆（隨不同觀看時間）。

### QuoteHighlight
- id (UUID)
- entry_id (UUID FK)
- original_text (text)
- 	ranslated_text (text, nullable)
- 	imestamp_hint (varchar, ex: 01:12:30)
- char_length 檢核上限 280。

### Tag
- slug (text, pk)
- 	ype (enum: GENRE / YEAR / LANGUAGE / CUSTOM)
- label (text)
- **關聯**：M:N ViewingEntry（透過連接表 entry_tags）。

### DiscussionThread
- id (UUID)
- work_id 或 	ag_slug（兩者擇一）
- 	itle / ody
- uthor_id
- status (enum: ACTIVE / LOCKED)
- **關聯**：1:N DiscussionReply。

### DiscussionReply
- id (UUID)
- 	hread_id (UUID)
- parent_id (UUID nullable，支援巢狀)
- uthor_id
- ody
- quoted_text (text nullable)
- status (enum: ACTIVE / TEMP_REMOVED / REMOVED)
- moderation_case_id (nullable)

### ModerationCase
- id (UUID)
- 	arget_type (THREAD_REPLY / THREAD)
- 	arget_id
- status (enum: PENDING / UNDER_REVIEW / RESOLVED / REJECTED)
- opened_at / closed_at
- 
otes

### FocusSubscription
- id (UUID)
- ollower_id
- uthor_id
- 
otification_frequency (enum: INSTANT / DAILY / WEEKLY)
- created_at

### Notification
- id (UUID)
- ecipient_id
- 	ype (NEW_ENTRY / NEW_REPLY / MODERATION_UPDATE)
- payload (jsonb)
- ead_at (timestamp nullable)

### UserProfile
- id (UUID)
- display_name
- ole (GENERAL / PROFESSIONAL / ADMIN)
- io (text)
- vatar_url
- stats (jsonb: avg_score, entry_count, tag_usage)
- adge_status (enum: NONE / PENDING / APPROVED)

### AggregateScore (View)
- 來源：iewing_entries
- 欄位：work_id, vg_general_score, vg_pro_score, weighted_score
- 計算：weighted_score = (avg_pro * 2 + avg_general) / 3。

## 狀態流程
- **專注通知**：FocusSubscription -> Notification queue -> WebSocket；若 6 小時內未送達，需要標記補償任務。
- **檢舉**：DiscussionReply.status 由使用者檢舉轉為 TEMP_REMOVED，ModerationCase 於 24h 內決議後更新為 RESOLVED/REJECTED。
- **Badge**：UserProfile.badge_status 可由系統建議 -> Admin 核准。

## 資料型量體
- ViewingEntry：每日最多 5k 新增，保留近 3 年約 5M 筆。
- DiscussionReply：預估 3 倍於紀錄量（15M），需索引 	hread_id、created_at。
- Notification：每日 20k 筆，需背景任務定期清理 read_at > 90 天資料。
