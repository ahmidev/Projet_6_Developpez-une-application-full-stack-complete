import { Component, OnDestroy, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ArticleService } from 'src/app/services/article.service';
import { User } from 'src/app/common/models/user';
import { SessionService } from 'src/app/services/session.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { TopicService } from 'src/app/services/topic.service';
import { Topic } from 'src/app/common/models/topic';
import { UserResponse } from 'src/app/common/models/userResponse';
import { NewArticle } from 'src/app/common/models/newArticle';
import { TopicsResponse } from 'src/app/common/models/topicsResponse';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-create-article',
  templateUrl: './create-article.component.html',
  styleUrls: ['./create-article.component.scss']
})
export class CreateArticleComponent implements OnInit, OnDestroy  {

  articleForm!: FormGroup;
  user!: UserResponse;
  themes: Topic[] = [];


  private themeSubscription!: Subscription;
  private articleSubscription!: Subscription;

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
    }
  }

  loadThemes(): void {
    this.themeSubscription =  this.topicService.getAllTopics().subscribe(
      (response: TopicsResponse) => {
        this.themes = response.topics;
      },
      (error) => {
      }
    );
  }

  onSubmit(): void {
    if (this.articleForm.valid && this.user) {
      const newArticle: NewArticle = {
        topicId: this.articleForm.value.theme,
        userId: this.user.id,
        title: this.articleForm.value.title,
        content: this.articleForm.value.content
      };
      this.articleSubscription = this.articleService.createArticle(newArticle).subscribe(
        (response) => {
          this.router.navigate(['/article-feed']);
        },
        (error) => {
        }
      );
    } else {
    }
  }


  goBack(): void {
    this.location.back();
  }

  ngOnDestroy(): void {
    if (this.themeSubscription) {
      this.themeSubscription.unsubscribe();
    }
    if (this.articleSubscription) {
      this.articleSubscription.unsubscribe();
    }
  }
}
