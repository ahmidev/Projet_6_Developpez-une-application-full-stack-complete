import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { User } from '../common/models/user';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  // public isLogged = false;
  // public user: User | undefined;
  
  // constructor() {this.loadUserFromLocalStorage(); }

  // private isLoggedSubject = new BehaviorSubject<boolean>(this.isLogged);

  // public $isLogged(): Observable<boolean> {
  //   return this.isLoggedSubject.asObservable();
  // }

  // public logIn(user: User): void {
  //   this.user = user;
  //   this.isLogged = true;
  //   localStorage.setItem('user', JSON.stringify(user));
  //   this.next();
  // }

  // public logOut(): void {
  //   localStorage.removeItem('token');
  //   localStorage.removeItem('user'); 
  //   this.user = undefined;
  //   this.isLogged = false;
  //   this.next();
  // }


  // private loadUserFromLocalStorage(): void {
  //   const storedUser = localStorage.getItem('user');
  //   if (storedUser) {
  //     this.user = JSON.parse(storedUser); 
  //     this.isLogged = true;
  //     this.next();
  //   }
  // }

  // public updateUserSubscriptions(updatedUser: User): void {
  //   this.user = updatedUser;
  //   this.updateLocalStorage(); 
  //   this.next();
  // }

  // private updateLocalStorage(): void {
  //   if (this.user) {
  //     localStorage.setItem('user', JSON.stringify(this.user)); 
  //   }
  // }
  // updateToken(newToken: string): void {
  //   localStorage.setItem('token', newToken);
  // }

  // private next(): void {
  //   this.isLoggedSubject.next(this.isLogged);
  // }

  private userSubject = new BehaviorSubject<User | undefined>(undefined);  // Pour garder une trace de l'utilisateur
  public user$: Observable<User | undefined> = this.userSubject.asObservable();

  private isLoggedSubject = new BehaviorSubject<boolean>(false);  // Gérer l'état de connexion
  public isLogged$: Observable<boolean> = this.isLoggedSubject.asObservable();

  constructor() {
    this.loadUserFromLocalStorage(); // Charger l'utilisateur depuis le localStorage au démarrage
  }

  // Méthode pour charger l'utilisateur et le token du localStorage
  private loadUserFromLocalStorage(): void {
    const storedUser = localStorage.getItem('user');
    const storedToken = localStorage.getItem('token');

    if (storedUser && storedToken) {
      this.userSubject.next(JSON.parse(storedUser));
      this.isLoggedSubject.next(true);
    }
  }

  // Méthode pour authentifier l'utilisateur (connexion)
  public logIn(user: User): void {
    localStorage.setItem('user', JSON.stringify(user));  // Stocker l'utilisateur dans le localStorage
    this.userSubject.next(user);
    this.isLoggedSubject.next(true);  // Marquer l'utilisateur comme connecté
  }

  // Méthode pour déconnecter l'utilisateur
  public logOut(): void {
    localStorage.removeItem('user');
    localStorage.removeItem('token');
    this.userSubject.next(undefined);
    this.isLoggedSubject.next(false);  // Marquer l'utilisateur comme déconnecté
  }

  // Mise à jour du token
  public updateToken(newToken: string): void {
    localStorage.setItem('token', newToken);  // Mettre à jour le token
  }

  // Mettre à jour les abonnements de l'utilisateur et le stocker dans le localStorage
  public updateUserSubscriptions(updatedUser: User): void {
    this.userSubject.next(updatedUser);
    localStorage.setItem('user', JSON.stringify(updatedUser));
  }

  // Getter pour vérifier si l'utilisateur est connecté
  public isLogged(): boolean {
    return this.isLoggedSubject.value;
  }

  // Obtenir l'utilisateur actuel
  public getUser(): User | undefined {
    return this.userSubject.value;
  }
  
}
