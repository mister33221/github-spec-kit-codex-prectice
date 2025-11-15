import { Injectable } from '@angular/core';

type ThemeMode = 'dark' | 'high-contrast' | 'light';

@Injectable({ providedIn: 'root' })
export class ThemeService {
  private mode: ThemeMode = 'dark';

  init() {
    this.applyTheme(this.mode);
  }

  applyTheme(mode: ThemeMode) {
    this.mode = mode;
    document.body.dataset['theme'] = mode;
  }
}
