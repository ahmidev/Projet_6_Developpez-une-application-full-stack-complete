import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { RegisterRequest } from '../common/models/registerRequest';
import { AuthSuccess } from '../common/models/authSuccess';
import { LoginRequest } from '../common/models/loginRequest';
import { Subscriptions } from '../common/models/subscriptions';
import { UserResponse } from '../common/models/userResponse';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private pathService = environment.baseUrl + 'auth';

  constructor(private httpClient: HttpClient) { }

  public register(registerRequest: RegisterRequest): Observable<AuthSuccess> {
    return this.httpClient.post<AuthSuccess>(`${this.pathService}/register`, registerRequest);
  }

  public login(loginRequest: LoginRequest): Observable<AuthSuccess> {
    return this.httpClient.post<AuthSuccess>(`${this.pathService}/login`, loginRequest);
  }

  public me(): Observable<UserResponse> {
    return this.httpClient.get<UserResponse>(`${this.pathService}/me`);
  }
}
