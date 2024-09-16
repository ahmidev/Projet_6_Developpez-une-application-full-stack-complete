import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Article } from 'src/app/common/models/article';
import { User } from 'src/app/common/models/user';
import { ArticleService } from 'src/app/services/article.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-article-feed',
  templateUrl: './article-feed.component.html',
  styleUrls: ['./article-feed.component.scss']
})
export class ArticleFeedComponent implements OnInit {

  currentUser!: User;
  articles!: Article[];
  isAscending: boolean = false;

  constructor(
    private articleService: ArticleService,
    private userService: UserService,
    private toastr: ToastrService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.userService.getAuthenticatedUser().subscribe({
      next: (user: User) => {
        this.currentUser = user;
        console.log('Current user:', this.currentUser);
        this.loadSubscribedArticles(this.currentUser.id);
      },
      error: (error: any) => {
        console.error('Error fetching authenticated user:', error);
        this.toastr.error('Erreur lors de la récupération de l\'utilisateur authentifié', 'Erreur');
      }
    });
  }

  loadSubscribedArticles(userId: number): void {
    this.articleService.getArticlesByUser(userId).subscribe({
      next: (data: Article[]) => {
        this.articles = data.map((item: any) => ({
          id: item.id,
          title: item.title,
          content: item.content,
          createdAt: item.createdAt,
          author: {
            id: item.user.id,
            name: item.user.name,
            email: item.user.email
          },
          topic: {
            id: item.topicDTO.id,
            name: item.topicDTO.name,
            articles: data.filter((article: any) => article.topicDTO.id === item.topicDTO.id) || null
          },
          comments: item.comments || null
        }));
        console.log('Articles:', this.articles);
        this.toastr.success('Articles chargés avec succès', 'Succès');
      },
      error: (error: any) => {
        console.error('Error fetching articles:', error);
        this.toastr.error('Erreur lors de la récupération des articles', 'Erreur');
      }
    });
  }

  viewArticle(articleId: number): void {
    this.router.navigate(['/article', articleId]);
  }

  sortArticlesByDate(): void {
    this.articles.sort((a, b) => {
      const dateA = new Date(a.createdAt!);
      const dateB = new Date(b.createdAt!);

      if (dateA && dateB) {
        return this.isAscending
          ? dateA.getTime() - dateB.getTime()
          : dateB.getTime() - dateA.getTime();
      }

      if (!dateA) return 1;
      if (!dateB) return -1;
      return 0;
    });

    this.isAscending = !this.isAscending;

    console.log('Articles triés par date:', this.articles);
    this.toastr.info('Articles triés par date', 'Information');
  }

}
