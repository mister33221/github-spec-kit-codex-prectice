import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'entries',
  },
  {
    path: 'entries',
    loadChildren: () =>
      import('./features/entry-editor/entry-editor.routes').then(
        (m) => m.ENTRY_EDITOR_ROUTES,
      ),
  },
  { path: '**', redirectTo: 'entries' },
];
