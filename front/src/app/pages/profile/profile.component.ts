import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NavigationEnd, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { filter } from 'rxjs';
import { Topic } from 'src/app/common/models/topic';
import { User } from 'src/app/common/models/user';
import { AuthService } from 'src/app/services/auth.service';
import { SessionService } from 'src/app/services/session.service';
import { SubscriptionService } from 'src/app/services/subscription.service';
import { TopicService } from 'src/app/services/topic.service';
import { UserService } from 'src/app/services/user.service';

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
  profileForm!: FormGroup; 

  constructor( private fb: FormBuilder, private topicService: TopicService, private subscriptionService: SubscriptionService, private authService: AuthService, private toastr: ToastrService, private sessionService: SessionService, private router: Router,  private cdr: ChangeDetectorRef, private userSerivce: UserService ) {}

  ngOnInit(): void {
    this.loadThemes();
    this.initForm();
    this.getUser();

  }

  loadThemes(): void {
    this.topicService.getAllTopics().subscribe(
      (response: any) => {
        console.log('###TOPIC#####',response.topics)
        //this.themes = response.topics;
        const allTopics: Topic[] = response.topics;
        this.themes = allTopics.filter(topic => this.subscribedTopicIds.includes(topic.id));
        this.cdr.detectChanges()
      },
      (error) => {
        console.error('Erreur lors du chargement des thèmes', error);
      }
    );
  }

  initForm(): void {
    this.profileForm = this.fb.group({
      username: [this.user?.name || '', Validators.required],
      email: [this.user?.email || '', [Validators.required, Validators.email]],
      password: ['', [
        Validators.minLength(8),
        Validators.pattern(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[\W_]).*$/)
      ]]
    });
  }

  getUser(): void {
    // this.authService.me().subscribe(user => {
    //   this.sessionService.updateUserSubscriptions(user); 
    //   this.userId = user.id;
    //   this.user = user;
    //   this.subscribedTopicIds = user.subscriptions?.map((sub: any) => sub.topicId);
    //   this.cdr.detectChanges()
    // }, (error) => {
    //   console.error('Erreur lors de la récupération des informations utilisateur', error);
    // });
    if (this.sessionService.getUser()) {
      this.userId = this.sessionService.getUser()!.id;  
      this.user = this.sessionService.getUser();
      this.subscribedTopicIds = this.user.subscriptions?.map((sub: any) => sub.topicId);
      this.initForm();
    } else {
      this.toastr.warning('Utilisateur non connecté', 'Attention');
    }
  }

  // onSubmit(): void {
  //   if (this.profileForm.valid) {
  //     const formData: User = this.profileForm.value;

  //     // Appel à l'API pour mettre à jour les informations de l'utilisateur
  //     this.userSerivce.updateUserProfile(formData).subscribe(
  //       (response) => {
  //         console.log('****************',response)
  //         this.toastr.success('Profil mis à jour avec succès', 'Succès');
  //         // Mettre à jour les informations utilisateur dans la session
  //         this.sessionService.updateUserSubscriptions(response);
  //       },
  //       (error) => {
  //         this.toastr.error('Erreur lors de la mise à jour du profil', 'Erreur');
  //         console.error('Erreur lors de la mise à jour du profil', error);
  //       }
  //     );
  //   } else {
  //     this.toastr.warning('Formulaire invalide', 'Attention');
  //   }
  // }
  
  onSubmit(): void {
    if (this.profileForm.valid) {
      const formData: any = {
        username: this.profileForm.get('username')?.value,
        email: this.profileForm.get('email')?.value
      };
  
      if (this.profileForm.get('password')?.value) {
        formData.password = this.profileForm.get('password')?.value;
      }
  
      this.userSerivce.updateUserProfile(formData).subscribe(
        (response: any) => {
          console.log('Profil mis à jour avec succès', response);
          this.toastr.success('Profil mis à jour avec succès', 'Succès');
          this.sessionService.updateUserSubscriptions(response);
          this.getUser();
          if (response.tocken) {
            this.sessionService.updateToken(response.tocken);
          }
        },
        (error) => {
          this.toastr.error('Erreur lors de la mise à jour du profil', 'Erreur');
          console.error('Erreur lors de la mise à jour du profil', error);
        }
      );
    } else {
      this.toastr.warning('Formulaire invalide', 'Attention');
    }
  }
  




  unsubscribeFromTheme(topicId: number): void {
    console.log(this.userId)
    this.subscriptionService.deleteSubscription(this.userId, topicId).subscribe(user => {
      this.toastr.success('Désabonnement réussi', 'Succès');
      this.sessionService.updateUserSubscriptions(user);  
      this.subscribedTopicIds = this.subscribedTopicIds.filter((id:number) => id !== topicId);
      this.loadThemes()
    }, (error) => {
      this.toastr.error('Erreur lors du désabonnement', 'Erreur');
      console.error('Erreur lors du désabonnement', error);
    });
  }

  isSubscribed(topicId: number): boolean {
    return this.subscribedTopicIds.includes(topicId);
  }

  logOut(){
    this.sessionService.logOut();
    this.router.navigate(['/home']); 
  }

}
