const importMetaEnv = (
  import.meta as ImportMeta & {
    env?: Record<string, string | undefined>;
  }
).env;

export const API_BASE_URL =
  (window as { __env?: { BACKEND_URL?: string } }).__env?.BACKEND_URL ??
  importMetaEnv?.['VITE_BACKEND_URL'] ??
  'http://localhost:8080';
