import { Author } from "./author";
import { Topic } from "./topic";

export interface Article {

    id : number;
    title: string;
    content: string;
    createdAt: Date | null;
    author: Author ;
    topic: any | null;
    comments: Comment[] | null;
  }