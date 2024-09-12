import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Topic } from 'src/app/common/models/topic';
import { AuthService } from 'src/app/services/auth.service';
import { SessionService } from 'src/app/services/session.service';
import { SubscriptionService } from 'src/app/services/subscription.service';
import { TopicService } from 'src/app/services/topic.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  themes: Topic[] = []; 
  userId!: number;
  subscribedTopicIds: number[] = [];
  user!: any;

  constructor(private topicService: TopicService, private subscriptionService: SubscriptionService, private authService: AuthService, private toastr: ToastrService  ) {}

  ngOnInit(): void {
    this.loadThemes(); 
    this.getUser();
  }

  loadThemes(): void {
    this.topicService.getAllTopics().subscribe(
      (response: any) => {
        console.log('###TOPIC#####',response.topics)
        //this.themes = response.topics;
        const allTopics: Topic[] = response.topics;
        this.themes = allTopics.filter(topic => this.subscribedTopicIds.includes(topic.id));
      },
      (error) => {
        console.error('Erreur lors du chargement des thèmes', error);
      }
    );
  }

  getUser(): void {
    this.authService.me().subscribe(user => {
      console.log('####USER####', user)
      this.userId =  user.id
      this.user = user
      this.subscribedTopicIds = user.subscriptions?.map((sub: any) => sub.topicId);
    })
    
  }

  
  unsubscribeFromTheme(topicId: number): void {
    this.subscriptionService.deleteSubscription(this.userId, topicId).subscribe(() => {
      this.subscribedTopicIds = this.subscribedTopicIds.filter(id => id !== topicId); 
      this.loadThemes(); 
      this.toastr.success('Désabonnement réussi', 'Succès');
    });
  }

  isSubscribed(topicId: number): boolean {
    return this.subscribedTopicIds.includes(topicId);
  }

}
