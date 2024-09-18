import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { Subscriptions } from "../../common/models/subscriptions";
import { Topic } from 'src/app/common/models/topic';
import { TopicsResponse } from 'src/app/common/models/topicsResponse';
import { UserDto } from 'src/app/common/models/userDto';
import { UserResponse } from 'src/app/common/models/userResponse';
import { SessionService } from 'src/app/services/session.service';
import { SubscriptionService } from 'src/app/services/subscription.service';
import { TopicService } from 'src/app/services/topic.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit, OnDestroy {

  themes: Topic[] = [];
  userId!: number;
  subscribedTopicIds: number[] = [];
  user!: UserResponse;
  profileForm!: FormGroup;


  private themeSubscription!: Subscription;
  private updateUserSubscription!: Subscription;
  private unsubscribeSubscription!: Subscription;

  constructor(private fb: FormBuilder,
    private topicService: TopicService,
    private subscriptionService: SubscriptionService,
    private toastr: ToastrService,
    private sessionService: SessionService,
    private router: Router,
    private cdr: ChangeDetectorRef,
    private userService: UserService) { }

  ngOnInit(): void {
    this.loadThemes();
    this.getUser();

  }

  loadThemes(): void {
    this.themeSubscription = this.topicService.getAllTopics().subscribe({
      next: (response: TopicsResponse) => {
        const allTopics: Topic[] = response.topics;
        this.themes = allTopics.filter(topic => this.subscribedTopicIds?.includes(topic.id));
        this.cdr.detectChanges();
      },
      error: (error) => {
        this.toastr.error('Erreur lors de la recuperation des themes', 'Erreur');
      }
    });
  }


  getUser(): void {
    if (this.sessionService.getUser()) {
      this.userId = this.sessionService.getUser()!.id;
      this.user = this.sessionService.getUser();
      this.subscribedTopicIds = this.user.subscriptions.map((sub: Subscriptions) => sub.topicId);
    } else {
      this.toastr.warning('Utilisateur non connecté', 'Attention');
    }
  }


  onSubmit(formData: UserDto): void {
    this.updateUserSubscription = this.userService.updateUserProfile(formData).subscribe({
      next: (response: UserResponse) => {
        this.toastr.success('Profil mis à jour avec succès', 'Succès');
        this.sessionService.updateUserSubscriptions(response);
        this.getUser();
        if (response.token) {
          this.sessionService.updateToken(response.token);
        }
      },
      error: (error) => {
        this.toastr.error('Erreur lors de la mise à jour du profil', 'Erreur');
      }
    });
  }


  unsubscribeFromTheme(topicId: number): void {
    this.unsubscribeSubscription = this.subscriptionService.deleteSubscription(this.userId, topicId).subscribe({
      next: (user) => {
        this.toastr.success('Désabonnement réussi', 'Succès');
        this.sessionService.updateUserSubscriptions(user);
        this.subscribedTopicIds = this.subscribedTopicIds?.filter((id: number) => id !== topicId);
        this.loadThemes();
      },
      error: (error) => {
        this.toastr.error('Erreur lors du désabonnement', 'Erreur');
      }
    });
  }

  isSubscribed(topicId: number): boolean {
    return this.subscribedTopicIds?.includes(topicId);
  }

  logOut() {
    this.sessionService.logOut();
    this.router.navigate(['/home']);
  }

  ngOnDestroy(): void {
    if (this.themeSubscription) {
      this.themeSubscription.unsubscribe();
    }
    if (this.updateUserSubscription) {
      this.updateUserSubscription.unsubscribe();
    }
    if (this.unsubscribeSubscription) {
      this.unsubscribeSubscription.unsubscribe();
    }
  }

}
