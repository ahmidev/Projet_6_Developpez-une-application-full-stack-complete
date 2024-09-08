import { Article } from "./article";

export interface Theme {
  id: number;
  name: string;
  articles: Article[] | null;
}