import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Article } from '../common/models/article';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {

  private pathService = environment.baseUrl ;

  constructor(private http: HttpClient) { }

  subscribeToArticle(userId: number, articleId: number): Observable<any> {
    return this.http.post<any>(`${this.pathService}subscriptions/article`, null, {
      params: {
        userId: userId.toString(),
        articleId: articleId.toString()
      }
    });
  }

  getSubscribedArticles(userId: number): Observable<Article[]> {
    return this.http.get<Article[]>(`${this.pathService}subscriptions/user/${userId}`);
  }

  unsubscribeFromArticle(userId: number, articleId: number): Observable<void> {
    return this.http.delete<void>(`${this.pathService}subscriptions/article/${articleId}/user/${userId}`);
  }
  
}
