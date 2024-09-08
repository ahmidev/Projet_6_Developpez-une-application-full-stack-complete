import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Article } from 'src/app/common/models/article';
import { User } from 'src/app/common/models/user';
import { ArticleService } from 'src/app/services/article.service';
import { SubscriptionService } from 'src/app/services/subscription.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-article-feed',
  templateUrl: './article-feed.component.html',
  styleUrls: ['./article-feed.component.scss']
})
export class ArticleFeedComponent implements OnInit {

  currentUser!: User;
  articles!: Article[];

  constructor(
    private articleService: ArticleService,
    private userService: UserService,
    private toastr: ToastrService  
  ) { }

  ngOnInit(): void {
    this.userService.getAuthenticatedUser().subscribe({
      next: (user: User) => {
        this.currentUser = user;
        console.log('Current user:', this.currentUser);
        //this.toastr.success('Utilisateur authentifié avec succès', 'Succès');
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
        this.articles = data;
        console.log('Articles:', this.articles);
        this.toastr.success('Articles chargés avec succès', 'Succès');
      },
      error: (error: any) => {
        console.error('Error fetching articles:', error);
        this.toastr.error('Erreur lors de la récupération des articles', 'Erreur');
      }
    });
  }

  sortArticlesByDate(): void {
    this.articles.sort((a, b) => {
      const dateA = new Date(a.createdAt as string);
      const dateB = new Date(b.createdAt as string);
      return dateB.getTime() - dateA.getTime(); // Tri décroissant
    });
    console.log('Articles triés par date:', this.articles);
    this.toastr.info('Articles triés par date', 'Information');
  }
}
