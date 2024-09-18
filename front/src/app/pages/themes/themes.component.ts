import { Component, OnDestroy, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { Subscriptions } from 'src/app/common/models/subscriptions';
import { Topic } from 'src/app/common/models/topic';
import { TopicsResponse } from 'src/app/common/models/topicsResponse';
import { UserResponse } from 'src/app/common/models/userResponse';
import { SessionService } from 'src/app/services/session.service';
import { SubscriptionService } from 'src/app/services/subscription.service';
import { TopicService } from 'src/app/services/topic.service';

@Component({
  selector: 'app-themes',
  templateUrl: './themes.component.html',
  styleUrls: ['./themes.component.scss']
})
export class ThemesComponent implements OnInit, OnDestroy {

  themes: Topic[] = [];
  userId!: number;
  subscribedTopicIds: number[] = [];
  user!: UserResponse;

  private themeSubscription!: Subscription;
  private subscriptionSubscription!: Subscription;

  constructor(private topicService: TopicService,
    private subscriptionService: SubscriptionService,
    private sessionService: SessionService,
    private toastr: ToastrService) { }

  ngOnInit(): void {
    this.loadThemes();
    this.getUser();
  }

  loadThemes(): void {
    this.themeSubscription = this.topicService.getAllTopics().subscribe({
      next: (response: TopicsResponse) => {
        this.themes = response.topics;
      },
      error: (error) => {
        this.toastr.error('Erreur lors du chargement des thèmes', 'Erreur');
      }
    });
  }

  getUser(): void {
    if (this.sessionService.getUser()) {
      this.userId = this.sessionService.getUser()!.id;
      this.user = this.sessionService.getUser();
      this.subscribedTopicIds = this.user.subscriptions?.map((sub: Subscriptions) => sub.topicId);
    } else {
      this.toastr.warning('Utilisateur non connecté', 'Attention');
    }
  }

  subscribeToTheme(topicId: number): void {
    if (this.isSubscribed(topicId)) {
      this.toastr.error('Utilisateur déjà abonné à ce thème', 'Erreur');
      return;
    }
    if (this.userId) {
      this.subscriptionSubscription = this.subscriptionService.createSubscription(this.userId, topicId).subscribe({
        next: (response) => {
          this.subscribedTopicIds.push(topicId);
          this.toastr.success('Abonnement réussi', 'Succès');
          this.sessionService.updateUserSubscriptions(response);
          this.getUser();
        },
        error: (error) => {
        }
      });
    } else {
      this.toastr.error('Utilisateur non connecté', 'Erreur');
    }
  }


  isSubscribed(topicId: number): boolean {
    return this.subscribedTopicIds.includes(topicId);
  }

  ngOnDestroy(): void {
    if (this.themeSubscription) {
      this.themeSubscription.unsubscribe();
    }
    if (this.subscriptionSubscription) {
      this.subscriptionSubscription.unsubscribe();
    }
  }
}
