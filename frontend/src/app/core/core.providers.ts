import { APP_INITIALIZER } from '@angular/core';
import {
  provideHttpClient,
  withInterceptorsFromDi,
} from '@angular/common/http';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { ThemeService } from './theme.service';

const initTheme = (themeService: ThemeService) => () => themeService.init();

export const CORE_PROVIDERS = [
  provideAnimationsAsync(),
  provideHttpClient(withInterceptorsFromDi()),
  ThemeService,
  {
    provide: APP_INITIALIZER,
    useFactory: initTheme,
    deps: [ThemeService],
    multi: true,
  },
];
