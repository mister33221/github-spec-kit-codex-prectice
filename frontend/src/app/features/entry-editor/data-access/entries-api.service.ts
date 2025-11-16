import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../../../core/api.config';
import {
  ViewingEntry,
  ViewingEntryRequestPayload,
} from '../models/viewing-entry.model';

@Injectable({
  providedIn: 'root',
})
export class EntriesApiService {
  private readonly http = inject(HttpClient);
  private readonly resourceUrl = `${API_BASE_URL}/api/entries`;

  listEntries(userId: string): Observable<ViewingEntry[]> {
    return this.http.get<ViewingEntry[]>(this.resourceUrl, {
      params: { userId },
    });
  }

  createEntry(payload: ViewingEntryRequestPayload): Observable<ViewingEntry> {
    return this.http.post<ViewingEntry>(this.resourceUrl, payload);
  }

  updateEntry(
    entryId: string,
    payload: ViewingEntryRequestPayload,
  ): Observable<ViewingEntry> {
    return this.http.patch<ViewingEntry>(
      `${this.resourceUrl}/${entryId}`,
      payload,
    );
  }
}
