import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CreateComment } from '../common/models/createComment';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private baseUrl = `${environment.baseUrl}comments`;

  constructor(private http: HttpClient) { }

  createComment(comment: CreateComment): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}`, comment);
  }
}
