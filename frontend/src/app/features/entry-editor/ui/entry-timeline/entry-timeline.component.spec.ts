import { ComponentFixture, TestBed } from '@angular/core/testing';
import { EntryTimelineComponent } from './entry-timeline.component';

describe('EntryTimelineComponent', () => {
  let fixture: ComponentFixture<EntryTimelineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EntryTimelineComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(EntryTimelineComponent);
    fixture.componentInstance.entries = [
      {
        id: '1',
        userId: 'user',
        workId: 'movie-01',
        score: 8,
        visibility: 'PUBLIC',
        tags: [],
        highlights: [],
      },
    ];
    fixture.detectChanges();
  });

  it('should render the provided entries', () => {
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelectorAll('.timeline-card').length).toBe(1);
  });

  it('should emit selection when record clicked', () => {
    spyOn(fixture.componentInstance.selectEntry, 'emit');
    const button = fixture.nativeElement.querySelector(
      '.timeline-card button',
    ) as HTMLButtonElement;
    button.click();
    expect(fixture.componentInstance.selectEntry.emit).toHaveBeenCalledWith(
      '1',
    );
  });
});
