import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Articles } from '../common/models/articles';
import { Observable } from 'rxjs';
import { Article } from '../common/models/article';
import { NewArticle } from '../common/models/newArticle';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  private pathService = environment.baseUrl
  
  
  constructor(private http: HttpClient) { }
  
  createArticle(post: NewArticle): Observable<Articles> {
    return this.http.post<Articles>(`${this.pathService}posts`, post);
  }

  getArticlesByUser(userId: number): Observable<Article[]> {
    return this.http.get<Article[]>(`${this.pathService}posts/user/${userId}`);
  }

  getArticleById(articleId: String): Observable<Article> {
    return this.http.get<Article>(`${this.pathService}posts/${articleId}`);
  }

}
