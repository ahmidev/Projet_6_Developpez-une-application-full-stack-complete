import { UserResponse } from "./userResponse";

export interface Comment {
    id: number;
    content: string;
    user: UserResponse;
  }