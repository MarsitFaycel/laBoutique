import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';
import { AccountService } from '../core/account.service';
import { AuthServerProvider } from '../core/auth/auth.service';
import { Login } from './login.model';

@Injectable({ providedIn: 'root' })
export class LoginService {
  constructor(
    private accountService: AccountService,
    private authServerProvider: AuthServerProvider
  ) {}

  login(credentials: Login): Observable<any | null> {
    return this.authServerProvider
      .login(credentials)
      .pipe(mergeMap(() => this.accountService.identity(true)));
  }

  logout(): void {
    this.authServerProvider
      .logout()
      .subscribe({ complete: () => this.accountService.authenticate(null) });
  }
}
