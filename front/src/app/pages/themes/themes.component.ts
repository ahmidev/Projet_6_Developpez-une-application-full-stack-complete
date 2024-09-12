import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { filter } from 'rxjs';
import { Topic } from 'src/app/common/models/topic';
import { User } from 'src/app/common/models/user';
import { SessionService } from 'src/app/services/session.service';
import { SubscriptionService } from 'src/app/services/subscription.service';
import { TopicService } from 'src/app/services/topic.service';

@Component({
  selector: 'app-themes',
  templateUrl: './themes.component.html',
  styleUrls: ['./themes.component.scss']
})
export class ThemesComponent implements OnInit {

  // themes: Topic[] = []; 
  // userId!: number;
  // subscribedTopicIds: number[] = [];
  // user!: any

  // constructor(private topicService: TopicService,  private subscriptionService: SubscriptionService,  
  //   private sessionService: SessionService ) {}

  // ngOnInit(): void {
  //   this.loadThemes(); 
  //   this.getUser()

  // }

  // loadThemes(): void {
  //   this.topicService.getAllTopics().subscribe(
  //     (response:any) => {
  //       console.log(response.topics);
  //       this.themes = response.topics;
  //     },
  //     (error) => {
  //       console.error('Erreur lors du chargement des thèmes', error);
  //     }
  //   );
  // }

  // getUser(): void {
  //   if (this.sessionService.user) {
  //     this.userId = this.sessionService.user.id;  
  //     this.user = this.sessionService.user;
  //     this.subscribedTopicIds = this.user.subscriptions?.map((sub:  any) => sub.topicId);
  //     console.log('Utilisateur connecté', this.user);
  //   }
  // }

  // subscribeToTheme(topicId: number): void {
  //   console.log(`Abonnement au thème avec ID : ${topicId}`);
  //   if (this.userId) {
  //     this.subscriptionService.createSubscription(this.userId, topicId).subscribe(
  //       (response) => {
  //         console.log('Abonnement réussi:', response);
  //         this.subscribedTopicIds.push(topicId);
  //       },
  //       (error) => {
  //         console.error('Erreur lors de l\'abonnement:', error);
  //       }
  //     );
  //   } else {
  //     console.error('Utilisateur non connecté');
  //   }
  // }
  
  // isSubscribed(topicId: number): boolean {
  //   return this.subscribedTopicIds.includes(topicId);
  // }
  themes: Topic[] = []; 
  userId!: number;
  subscribedTopicIds: number[] = [];
  user!: any;

  constructor(private topicService: TopicService, private subscriptionService: SubscriptionService, private sessionService: SessionService, private toastr: ToastrService, private router: Router  ) {}

  ngOnInit(): void {
    this.loadThemes(); 
    this.getUser();
  }

  loadThemes(): void {
    this.topicService.getAllTopics().subscribe(
      (response: any) => {
        this.themes = response.topics;
      },
      (error) => {
        this.toastr.error('Erreur lors du chargement des thèmes', 'Erreur');
        console.error('Erreur lors du chargement des thèmes', error);
      }
    );
  }

  getUser(): void {
    if (this.sessionService.getUser()) {
      this.userId = this.sessionService.getUser()!.id;  
      this.user = this.sessionService.getUser();
      this.subscribedTopicIds = this.user.subscriptions?.map((sub: any) => sub.topicId);
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
      this.subscriptionService.createSubscription(this.userId, topicId).subscribe(
        (response) => {
          console.log('reponse suscrip#####', response)
          this.subscribedTopicIds.push(topicId);
          this.toastr.success('Abonnement réussi', 'Succès');
          this.sessionService.updateUserSubscriptions(response); 
          this. getUser();
        },
        (error) => {
          console.error('Erreur lors de l\'abonnement:', error);
        }
      );
    } else {
      console.error('Utilisateur non connecté');
    }
  }
  
  isSubscribed(topicId: number): boolean {
    return this.subscribedTopicIds.includes(topicId);
  }
}
