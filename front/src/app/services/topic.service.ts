import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Topic } from '../common/models/topic';

@Injectable({
  providedIn: 'root'
})
export class TopicService {

  private baseUrl = environment.baseUrl;

  constructor(private http: HttpClient) { }

  getAllTopics(): Observable<Topic[]> {
    return this.http.get<Topic[]>(`${this.baseUrl}topics`);
  }
}
