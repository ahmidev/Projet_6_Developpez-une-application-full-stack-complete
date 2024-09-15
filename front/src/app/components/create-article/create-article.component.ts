import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ArticleService } from 'src/app/services/article.service';
import { User } from 'src/app/common/models/user';
import { SessionService } from 'src/app/services/session.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { TopicService } from 'src/app/services/topic.service';
import { Topic } from 'src/app/common/models/topic';

@Component({
  selector: 'app-create-article',
  templateUrl: './create-article.component.html',
  styleUrls: ['./create-article.component.scss']
})
export class CreateArticleComponent implements OnInit {

  articleForm!: FormGroup;
  user!: User;
  themes: Topic[] = [];


  constructor(private location: Location,
    private fb: FormBuilder,
    private articleService: ArticleService,
    private sessionService: SessionService,
    private toastr: ToastrService,
    private router: Router,
    private topicService: TopicService) { }


  ngOnInit(): void {
    this.initForm();
    this.getUser();
    this.loadThemes();
  }


  initForm(): void {
    this.articleForm = this.fb.group({
      theme: ['', Validators.required],
      title: ['', Validators.required],
      content: ['', Validators.required]
    });
  }

  getUser(): void {
    if (this.sessionService.getUser()) {
      this.user = this.sessionService.getUser()!;
    } else {
      console.log('Utilisateur non trouvé dans la session.');
    }
  }

  loadThemes(): void {
    this.topicService.getAllTopics().subscribe(
      (response: any) => {
        this.themes = response.topics;
      },
      (error) => {
        console.error('Erreur lors du chargement des thèmes', error);
      }
    );
  }

  onSubmit(): void {
    if (this.articleForm.valid && this.user) {
      const newArticle: any = {
        topicId: this.articleForm.value.theme,
        userId: this.user.id,
        title: this.articleForm.value.title,
        content: this.articleForm.value.content
      };
      this.articleService.createArticle(newArticle).subscribe(
        (response) => {
          console.log('Article créé avec succès :', response);
          this.router.navigate(['/article-feed']);
        },
        (error) => {
          console.error('Erreur lors de la création de l\'article :', error);
        }
      );
    } else {
      console.log('Formulaire invalide ou utilisateur non connecté.');
    }
  }


  goBack(): void {
    this.location.back();
  }
}
