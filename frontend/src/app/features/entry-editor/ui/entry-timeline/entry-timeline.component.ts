import {
  ChangeDetectionStrategy,
  Component,
  EventEmitter,
  Input,
  Output,
} from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  ViewingEntry,
  VIEWING_ENTRY_VISIBILITY_LABEL,
} from '../../models/viewing-entry.model';

@Component({
  selector: 'app-entry-timeline',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './entry-timeline.component.html',
  styleUrl: './entry-timeline.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class EntryTimelineComponent {
  @Input({ required: true }) entries: ViewingEntry[] | null = [];
  @Input() activeEntryId: string | null = null;
  @Input() loading = false;
  @Output() selectEntry = new EventEmitter<string | null>();

  protected readonly visibilityLabel = VIEWING_ENTRY_VISIBILITY_LABEL;

  trackById = (_: number, entry: ViewingEntry) => entry.id ?? entry.workId;

  createNew(): void {
    this.selectEntry.emit(null);
  }

  onSelect(entryId: string | undefined): void {
    this.selectEntry.emit(entryId ?? null);
  }
}
