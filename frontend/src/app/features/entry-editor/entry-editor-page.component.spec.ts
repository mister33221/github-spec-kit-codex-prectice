import { TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { EntryEditorPageComponent } from './entry-editor-page.component';
import { EntriesApiService } from './data-access/entries-api.service';

interface CalendarDayStub {
  date: Date;
  label: number;
  inCurrentMonth: boolean;
  isToday: boolean;
  isSelected: boolean;
}

describe('EntryEditorPageComponent', () => {
  let component: EntryEditorPageComponent;
  let entriesApi: jasmine.SpyObj<EntriesApiService>;

  beforeEach(async () => {
    entriesApi = jasmine.createSpyObj<EntriesApiService>('EntriesApiService', [
      'createEntry',
      'updateEntry',
    ]);
    entriesApi.createEntry.and.returnValue(
      of({
        id: 'new-entry',
        userId: '11111111-1111-1111-1111-111111111111',
        workId: 'work-001',
        watchedAt: null,
        score: 7,
        visibility: 'PUBLIC',
        reviewBody: '',
        tags: [],
        highlights: [],
      }),
    );

    await TestBed.configureTestingModule({
      imports: [EntryEditorPageComponent],
      providers: [{ provide: EntriesApiService, useValue: entriesApi }],
    }).compileComponents();

    component = TestBed.createComponent(
      EntryEditorPageComponent,
    ).componentInstance;
  });

  it('should create component and expose calendar controls', () => {
    expect(component).toBeTruthy();
    expect(component.calendarVisible()).toBeFalse();
    component.toggleCalendar();
    expect(component.calendarVisible()).toBeTrue();
  });

  it('should submit new entries when form is valid', () => {
    component.controls.workId.setValue('work-001');
    component.controls.tagsText.setValue('test');
    component.saveEntry();
    expect(entriesApi.createEntry).toHaveBeenCalled();
  });

  it('should reset to new entry mode', () => {
    component.controls.entryId.setValue('existing');
    component.resetFormForNewEntry();
    expect(component.controls.entryId.value).toBeNull();
  });

  it('should update date when selecting calendar day', () => {
    const initial = component.controls.watchedAt.value;
    const day: CalendarDayStub = {
      date: new Date('2025-04-02T00:00:00Z'),
      label: 2,
      inCurrentMonth: true,
      isToday: false,
      isSelected: false,
    };
    component.selectCalendarDay(day);
    expect(component.controls.watchedAt.value).not.toEqual(initial);
    expect(component.controls.watchedAt.value).toContain('2025-04-02');
  });
});
