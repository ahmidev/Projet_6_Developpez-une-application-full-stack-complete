import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ArticleService } from 'src/app/services/article.service';
import { Location } from '@angular/common';
import { SessionService } from 'src/app/services/session.service';
import { ToastrService } from 'ngx-toastr';
import { CommentService } from 'src/app/services/comment.service';
import { CreateComment } from 'src/app/common/models/createComment';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-article-card',
  templateUrl: './article-card.component.html',
  styleUrls: ['./article-card.component.scss']
})
export class ArticleCardComponent implements OnInit {

  article!: any;
  commentForm!: FormGroup;;
  currentUser: any;


  constructor(
    private route: ActivatedRoute,
    private articleService: ArticleService,
    private location: Location,
    private commentService: CommentService,
    private sessionService: SessionService,
    private fb: FormBuilder,
    private toastr: ToastrService
  ) { }


  ngOnInit(): void {
    this.loadArticle();
    this.initCommentForm();
    this.getCurrentUser();
  }


  initCommentForm(): void {
    this.commentForm = this.fb.group({
      content: ['', Validators.required]
    });
  }


  loadArticle(): void {
    const articleId = this.route.snapshot.paramMap.get('id');
    if (articleId) {
      this.articleService.getArticleById(articleId).subscribe({
        next: (data: any) => {
          this.article = data;
          console.log('Détails de l\'article:', this.article);
        },
        error: (error: any) => {
          console.error('Erreur lors de la récupération de l\'article:', error);
        }
      });
    }
  }

  getCurrentUser(): void {
    this.currentUser = this.sessionService.getUser();
  }


  submitComment(): void {
    if (this.commentForm.invalid) {
      this.toastr.warning('Le commentaire ne peut pas être vide', 'Attention');
      return;
    }

    const comment: CreateComment = {
      articleId: this.article.id,
      userId: this.currentUser.id,
      content: this.commentForm.get('content')?.value
    };

    this.commentService.createComment(comment).subscribe({
      next: (response) => {
        this.toastr.success('Commentaire ajouté avec succès', 'Succès');
        this.article.comments.push(response);
        this.commentForm.reset();
      },
      error: (error) => {
        console.error('Erreur lors de l\'ajout du commentaire', error);
        this.toastr.error('Erreur lors de l\'ajout du commentaire', 'Erreur');
      }
    });
  }


  goBack(): void {
    this.location.back();
  }
}