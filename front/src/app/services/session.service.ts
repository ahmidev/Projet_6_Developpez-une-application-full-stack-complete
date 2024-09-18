import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { User } from '../common/models/user';
import { UserResponse } from '../common/models/userResponse';

@Injectable({
  providedIn: 'root'
})
export class SessionService {


  private userSubject = new BehaviorSubject<UserResponse  | undefined>(undefined);
  public user$: Observable<UserResponse  | undefined> = this.userSubject.asObservable();

  private isLoggedSubject = new BehaviorSubject<boolean>(false);
  public isLogged$: Observable<boolean> = this.isLoggedSubject.asObservable();

  constructor() {
    this.loadUserFromLocalStorage();
  }

  private loadUserFromLocalStorage(): void {
    const storedUser = localStorage.getItem('user');
    const storedToken = localStorage.getItem('token');

    if (storedUser && storedToken) {
      this.userSubject.next(JSON.parse(storedUser));
      this.isLoggedSubject.next(true);
    }
  }

  public logIn(user: UserResponse ): void {
    localStorage.setItem('user', JSON.stringify(user));
    this.userSubject.next(user);
    this.isLoggedSubject.next(true);
  }

  public logOut(): void {
    localStorage.removeItem('user');
    localStorage.removeItem('token');
    this.userSubject.next(undefined);
    this.isLoggedSubject.next(false);
  }

  public updateToken(newToken: string): void {
    localStorage.setItem('token', newToken);
  }

  public updateUserSubscriptions(updatedUser: UserResponse ): void {
    this.userSubject.next(updatedUser);
    localStorage.setItem('user', JSON.stringify(updatedUser));
  }

  public isLogged(): boolean {
    return this.isLoggedSubject.value;
  }

  public getUser(): UserResponse {
    return this.userSubject.value!;
  }

}
