import { Topic } from "./topic";
import { User } from "./user";

export interface Articles {

  title: string;
  content: string;
  createdAt: String | null;
  author: User ;
  theme: Topic | null;
  comments: Comment[] | null;
}