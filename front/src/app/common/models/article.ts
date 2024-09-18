import { Comment } from "./comment";
import { Topic } from "./topic";
import { UserResponse } from "./userResponse";
export interface Article {

  id: number;
  title: string;
  content: string;
  user: UserResponse;
  topicDTO: Topic;
  comments: Comment[];
  createdAt: string; 
  }