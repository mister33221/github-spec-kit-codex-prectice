export type ViewingEntryVisibility = 'PUBLIC' | 'FOLLOWERS' | 'PRIVATE';

export interface QuoteHighlight {
  originalText: string;
  translatedText?: string | null;
  timestampHint?: string | null;
}

export interface ViewingEntry {
  id?: string;
  userId: string;
  workId: string;
  watchedAt?: string | null;
  score: number;
  visibility: ViewingEntryVisibility;
  reviewBody?: string | null;
  tags: string[];
  highlights: QuoteHighlight[];
}

export interface ViewingEntryRequestPayload {
  userId: string;
  workId: string;
  watchedAt?: string | null;
  score: number;
  visibility: ViewingEntryVisibility;
  reviewBody?: string | null;
  tags: string[];
  highlights: QuoteHighlight[];
}

export const VIEWING_ENTRY_VISIBILITY_LABEL: Record<
  ViewingEntryVisibility,
  string
> = {
  PUBLIC: '公開',
  FOLLOWERS: '限追蹤者',
  PRIVATE: '私人',
};
