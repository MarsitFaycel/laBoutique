import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Login } from 'src/app/login/login.model';
import { ApplicationConfigService } from '../config/application-config.service';

@Injectable({ providedIn: 'root' })
export class AuthServerProvider {
  constructor(
    private http: HttpClient,

    private applicationConfigService: ApplicationConfigService
  ) {}

  login(credentials: Login): Observable<void> {
    return this.http.post<any>(
      this.applicationConfigService.getEndpointFor('/login'),
      credentials
    );
  }

  logout(): Observable<void> {
    return new Observable((observer) => {
      observer.complete();
    });
  }
}
