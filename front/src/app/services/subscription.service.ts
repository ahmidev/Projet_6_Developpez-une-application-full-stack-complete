import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {

  private baseUrl = environment.baseUrl+"subscription" ;

  constructor(private http: HttpClient) { }

  createSubscription(userId: number, topicId: number): Observable<any> {
    const subscriptionData = { topicId, userId };
    return this.http.post(`${this.baseUrl}`, subscriptionData);
  }

  deleteSubscription(userId: number, topicId: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${userId}/${topicId}`);
  }

  
  
}
