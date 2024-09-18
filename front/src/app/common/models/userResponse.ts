import { Subscriptions } from "./subscriptions";

export interface UserResponse {
    id: number;
    email: string;
    userName: string;
    token?: string;
    subscriptions: Subscriptions[];
    created_at: string; 
    updated_at: string; 
  }