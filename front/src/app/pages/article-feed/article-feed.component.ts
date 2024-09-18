import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { Article } from 'src/app/common/models/article';
import { User } from 'src/app/common/models/user';
import { ArticleService } from 'src/app/services/article.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-article-feed',
  templateUrl: './article-feed.component.html',
  styleUrls: ['./article-feed.component.scss']
})
export class ArticleFeedComponent implements OnInit, OnDestroy {

  currentUser!: User;
  articles!: Article[];
  isAscending: boolean = false;

  private userSubscription!: Subscription;
  private articleSubscription!: Subscription;

  constructor(
    private articleService: ArticleService,
    private userService: UserService,
    private toastr: ToastrService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.userSubscription = this.userService.getAuthenticatedUser().subscribe({
      next: (user: User) => {
        this.currentUser = user;
        this.loadSubscribedArticles(this.currentUser.id);
      },
      error: (error: Error) => {
        this.toastr.error('Erreur lors de la récupération de l\'utilisateur authentifié', 'Erreur');
      }
    });
  }

  loadSubscribedArticles(userId: number): void {
    this.articleSubscription = this.articleService.getArticlesByUser(userId).subscribe({
      next: (data: Article[]) => {
        this.articles = data.map((item: Article) => ({
          id: item.id,
          title: item.title,
          content: item.content,
          createdAt: item.createdAt,
          user: {
            id: item.user.id,
            userName: item.user.userName, 
            email: item.user.email,
            subscriptions: item.user.subscriptions, 
            created_at: item.user.created_at, 
            updated_at: item.user.updated_at 
          },
          topicDTO: {
            id: item.topicDTO.id,
            name: item.topicDTO.name,
            description: item.topicDTO.description, 
            subscriptions: item.topicDTO.subscriptions, 
            articles: data.filter((article: Article) => article.topicDTO.id === item.topicDTO.id) || null
          },
          comments: item.comments || []
        }));
        this.toastr.success('Articles chargés avec succès', 'Succès');
      },
      error: (error: Error) => {
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

    this.toastr.info('Articles triés par date', 'Information');
  }

  ngOnDestroy(): void {
    if (this.userSubscription) {
      this.userSubscription.unsubscribe();
    }
    if (this.articleSubscription) {
      this.articleSubscription.unsubscribe();
    }
  }

}
