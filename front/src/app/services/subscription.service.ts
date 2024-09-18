import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { UserResponse } from '../common/models/userResponse';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {

  private baseUrl = environment.baseUrl+"subscription" ;

  constructor(private http: HttpClient) { }

  createSubscription(userId: number, topicId: number): Observable<UserResponse> {
    const subscriptionData = { topicId, userId };
    return this.http.post<UserResponse>(`${this.baseUrl}`, subscriptionData);
  }

  deleteSubscription(userId: number, topicId: number): Observable<UserResponse> {
    return this.http.delete<UserResponse>(`${this.baseUrl}/${userId}/${topicId}`);
  }

  
  
}
