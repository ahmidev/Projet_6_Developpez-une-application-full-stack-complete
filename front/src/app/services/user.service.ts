import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { User } from '../common/models/user';
import { Observable } from 'rxjs';
import { Author } from '../common/models/author';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = environment.baseUrl;

  constructor(private http: HttpClient) { }

  getAuthenticatedUser(): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}auth/me`);
  }

  updateUserProfile( user: User): Observable<User> {
    return this.http.put<User>(`${this.baseUrl}users/auth/me`, user);
  }
}
