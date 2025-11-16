import {
  ChangeDetectionStrategy,
  Component,
  computed,
  inject,
  signal,
} from '@angular/core';
import {
  FormArray,
  FormBuilder,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { finalize } from 'rxjs/operators';
import { EntriesApiService } from './data-access/entries-api.service';
import {
  QuoteHighlight,
  ViewingEntry,
  ViewingEntryRequestPayload,
  ViewingEntryVisibility,
  VIEWING_ENTRY_VISIBILITY_LABEL,
} from './models/viewing-entry.model';

type HighlightFormGroup = FormGroup<{
  originalText: FormControl<string>;
  translatedText: FormControl<string>;
  timestampHint: FormControl<string>;
}>;

interface CalendarDay {
  date: Date;
  label: number;
  inCurrentMonth: boolean;
  isToday: boolean;
  isSelected: boolean;
}

const DEMO_USER_ID = '11111111-1111-1111-1111-111111111111';

@Component({
  selector: 'app-entry-editor-page',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './entry-editor-page.component.html',
  styleUrl: './entry-editor-page.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class EntryEditorPageComponent {
  private readonly fb = inject(FormBuilder);
  private readonly entriesApi = inject(EntriesApiService);

  readonly visibilityOptions: ViewingEntryVisibility[] = [
    'PUBLIC',
    'FOLLOWERS',
    'PRIVATE',
  ];
  readonly visibilityLabel = VIEWING_ENTRY_VISIBILITY_LABEL;

  readonly entryForm = this.fb.group({
    entryId: this.fb.control<string | null>(null),
    userId: this.fb.control(DEMO_USER_ID, {
      nonNullable: true,
      validators: [Validators.required],
    }),
    workId: this.fb.control('', {
      nonNullable: true,
      validators: [Validators.required],
    }),
    watchedAt: this.fb.control<string>(this.getLocalNow()),
    score: this.fb.control(7, {
      nonNullable: true,
      validators: [Validators.required, Validators.min(1), Validators.max(10)],
    }),
    visibility: this.fb.control<ViewingEntryVisibility>('PUBLIC', {
      nonNullable: true,
    }),
    reviewBody: this.fb.control(''),
    tagsText: this.fb.control(''),
    highlights: this.fb.array<HighlightFormGroup>([
      this.buildHighlightGroup(),
    ]),
  });

  readonly controls = this.entryForm.controls;
  readonly steps = [
    { label: '基本資訊', description: '作品、評分、心得' },
    { label: '進階細節', description: '標籤、金句、備註' },
  ];
  readonly currentStep = signal(0);
  readonly showTagsSection = signal(false);
  readonly showHighlightsSection = signal(false);
  readonly calendarVisible = signal(false);
  readonly calendarMonth = signal(this.getInitialCalendarMonth());
  readonly calendarDays = computed(() => this.buildCalendarDays());
  readonly weekdayLabels = ['日', '一', '二', '三', '四', '五', '六'];
  readonly saving = signal(false);
  readonly statusMessage = signal<string | null>(null);

  get highlightsArray(): FormArray<HighlightFormGroup> {
    return this.entryForm.controls.highlights;
  }

  addHighlight(highlight?: Partial<QuoteHighlight>): void {
    this.highlightsArray.push(this.buildHighlightGroup(highlight));
  }

  removeHighlight(index: number): void {
    if (this.highlightsArray.length === 1) {
      this.highlightsArray.at(0).reset({
        originalText: '',
        translatedText: '',
        timestampHint: '',
      });
      return;
    }

    this.highlightsArray.removeAt(index);
  }

  goToStep(index: number): void {
    const current = this.currentStep();
    if (index === current) {
      return;
    }

    if (index > current && !this.validateBasicStep()) {
      return;
    }

    if (index >= 0 && index < this.steps.length) {
      this.currentStep.set(index);
    }
  }

  nextStep(): void {
    const step = this.currentStep();
    if (step >= this.steps.length - 1) {
      this.saveEntry();
      return;
    }

    if (!this.validateBasicStep()) {
      return;
    }

    this.currentStep.set(step + 1);
  }

  previousStep(): void {
    const step = this.currentStep();
    if (step > 0) {
      this.currentStep.set(step - 1);
    }
  }

  toggleTagsSection(): void {
    this.showTagsSection.set(!this.showTagsSection());
  }

  toggleHighlightsSection(): void {
    this.showHighlightsSection.set(!this.showHighlightsSection());
  }

  saveEntry(): void {
    if (this.entryForm.invalid) {
      this.entryForm.markAllAsTouched();
      return;
    }

    const payload = this.toPayload();
    const entryId = this.entryForm.controls.entryId.value;
    this.saving.set(true);

    const request$ = entryId
      ? this.entriesApi.updateEntry(entryId, payload)
      : this.entriesApi.createEntry(payload);

    request$
      .pipe(finalize(() => this.saving.set(false)))
      .subscribe({
        next: (entry) => {
          this.statusMessage.set(
            entryId ? '紀錄已更新' : '紀錄已建立並加入時間軸',
          );
          this.populateForm(entry);
        },
        error: (err) => {
          this.statusMessage.set('儲存失敗，請稍後再試');
          console.error('Failed to save entry', err);
        },
      });
  }

  resetFormForNewEntry(): void {
    const userId = this.entryForm.controls.userId.value;
    this.entryForm.reset({
      entryId: null,
      userId,
      workId: '',
      watchedAt: this.getLocalNow(),
      score: 7,
      visibility: 'PUBLIC',
      reviewBody: '',
      tagsText: '',
    });
    this.highlightsArray.clear();
    this.highlightsArray.push(this.buildHighlightGroup());
    this.currentStep.set(0);
    this.showTagsSection.set(false);
    this.showHighlightsSection.set(false);
    this.statusMessage.set('已切換至新增模式');
  }

  private populateForm(entry: ViewingEntry): void {
    this.entryForm.patchValue({
      entryId: entry.id ?? null,
      userId: entry.userId,
      workId: entry.workId,
      watchedAt: this.toLocalInput(entry.watchedAt),
      score: entry.score,
      visibility: entry.visibility,
      reviewBody: entry.reviewBody ?? '',
      tagsText: entry.tags?.join(', ') ?? '',
    });

    this.highlightsArray.clear();
    if (entry.highlights?.length) {
      entry.highlights.forEach((highlight) =>
        this.highlightsArray.push(this.buildHighlightGroup(highlight)),
      );
    } else {
      this.highlightsArray.push(this.buildHighlightGroup());
    }

    this.showTagsSection.set((entry.tags?.length ?? 0) > 0);
    this.showHighlightsSection.set((entry.highlights?.length ?? 0) > 0);
    this.currentStep.set(0);
  }

  private toPayload(): ViewingEntryRequestPayload {
    const raw = this.entryForm.getRawValue();
    return {
      userId: raw.userId,
      workId: raw.workId,
      watchedAt: raw.watchedAt
        ? new Date(raw.watchedAt).toISOString()
        : null,
      score: raw.score,
      visibility: raw.visibility,
      reviewBody: raw.reviewBody?.trim() || null,
      tags: this.parseTags(raw.tagsText),
      highlights: this.highlightsArray.controls
        .map((control) => control.getRawValue())
        .filter((highlight) => highlight.originalText.trim().length > 0),
    };
  }

  private buildHighlightGroup(
    highlight?: Partial<QuoteHighlight>,
  ): HighlightFormGroup {
    return this.fb.group({
      originalText: this.fb.control(highlight?.originalText ?? '', {
        nonNullable: true,
        validators: [Validators.maxLength(280)],
      }),
      translatedText: this.fb.control(highlight?.translatedText ?? '', {
        nonNullable: true,
      }),
      timestampHint: this.fb.control(highlight?.timestampHint ?? '', {
        nonNullable: true,
      }),
    });
  }

  private parseTags(source?: string | null): string[] {
    if (!source) {
      return [];
    }

    return source
      .split(',')
      .map((tag) => tag.trim())
      .filter((tag) => tag.length > 0);
  }

  private toLocalInput(value?: string | null): string {
    if (!value) {
      return '';
    }

    return this.formatDateInput(new Date(value));
  }

  private toSortableDate(value?: string | null): number {
    return value ? new Date(value).getTime() : 0;
  }

  countTags(value?: string | null): number {
    if (!value) {
      return 0;
    }

    return value
      .split(',')
      .map((tag) => tag.trim())
      .filter((tag) => tag.length > 0).length;
  }

  formatDisplayDate(value?: string | null): string {
    const date = this.getWatchedDate(value);
    if (!date) {
      return '尚未選取日期';
    }

    return date.toLocaleDateString('zh-TW', {
      month: 'long',
      day: 'numeric',
    });
  }

  formatWeekdayDisplay(value?: string | null): string {
    const date = this.getWatchedDate(value);
    if (!date) {
      return '請選擇日期';
    }

    return date.toLocaleDateString('zh-TW', {
      weekday: 'long',
    });
  }

  toggleCalendar(): void {
    this.calendarVisible.set(!this.calendarVisible());
  }

  prevCalendarMonth(): void {
    const month = this.calendarMonth();
    this.calendarMonth.set(
      new Date(month.getFullYear(), month.getMonth() - 1, 1),
    );
  }

  nextCalendarMonth(): void {
    const month = this.calendarMonth();
    this.calendarMonth.set(
      new Date(month.getFullYear(), month.getMonth() + 1, 1),
    );
  }

  calendarMonthLabel(): string {
    const month = this.calendarMonth();
    return month.toLocaleDateString('zh-TW', {
      year: 'numeric',
      month: 'long',
    });
  }

  selectCalendarDay(day: CalendarDay): void {
    const nextDate = new Date(day.date);
    nextDate.setHours(12, 0, 0, 0);
    this.entryForm.controls.watchedAt.setValue(this.formatDateInput(nextDate));
    this.calendarMonth.set(
      new Date(nextDate.getFullYear(), nextDate.getMonth(), 1),
    );
    this.calendarVisible.set(false);
  }

  private buildCalendarDays(): CalendarDay[] {
    const month = this.calendarMonth();
    const firstDay = new Date(month.getFullYear(), month.getMonth(), 1);
    const startOffset = firstDay.getDay();
    const startDate = new Date(firstDay);
    startDate.setDate(firstDay.getDate() - startOffset);

    const days: CalendarDay[] = [];
    const cursor = new Date(startDate);
    const selected = this.getWatchedDate();
    const today = new Date();

    for (let i = 0; i < 42; i += 1) {
      const date = new Date(cursor);
      days.push({
        date,
        label: date.getDate(),
        inCurrentMonth: date.getMonth() === month.getMonth(),
        isToday: this.isSameDay(date, today),
        isSelected: selected ? this.isSameDay(date, selected) : false,
      });
      cursor.setDate(cursor.getDate() + 1);
    }

    return days;
  }

  private getWatchedDate(raw?: string | null): Date | null {
    const source = raw ?? this.entryForm.controls.watchedAt.value;
    if (!source) {
      return null;
    }
    return new Date(source);
  }

  private getInitialCalendarMonth(): Date {
    const date = this.getWatchedDate();
    if (!date) {
      return new Date();
    }
    return new Date(date.getFullYear(), date.getMonth(), 1);
  }

  private formatDateInput(date: Date): string {
    return `${date.getFullYear()}-${this.pad(date.getMonth() + 1)}-${this.pad(
      date.getDate(),
    )}T${this.pad(date.getHours())}:${this.pad(date.getMinutes())}`;
  }

  private pad(value: number): string {
    return value.toString().padStart(2, '0');
  }

  private isSameDay(a: Date, b: Date): boolean {
    return (
      a.getFullYear() === b.getFullYear() &&
      a.getMonth() === b.getMonth() &&
      a.getDate() === b.getDate()
    );
  }

  private validateBasicStep(): boolean {
    const { workId, score } = this.entryForm.controls;
    if (workId.invalid || score.invalid) {
      workId.markAsTouched();
      score.markAsTouched();
      return false;
    }

    return true;
  }

  private getLocalNow(): string {
    return this.formatDateInput(new Date());
  }
}
