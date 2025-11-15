export const API_BASE_URL =
  (window as { __env?: { BACKEND_URL?: string } }).__env?.BACKEND_URL ??
  (import.meta.env['VITE_BACKEND_URL'] as string | undefined) ??
  'http://localhost:8080';
