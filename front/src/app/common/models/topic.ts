import { Subscription } from "rxjs";

export interface Topic {
  id: number;
  name: string;
  description: string;
  subscriptions: Subscription[]; 
}
