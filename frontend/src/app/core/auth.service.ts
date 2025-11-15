import { Injectable, signal } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly _authenticated = signal(false);

  isAuthenticated() {
    return this._authenticated();
  }

  setAuthenticated(value: boolean) {
    this._authenticated.set(value);
  }
}
