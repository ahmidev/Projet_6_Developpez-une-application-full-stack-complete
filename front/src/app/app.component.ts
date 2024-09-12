import { Component } from '@angular/core';
import { SessionService } from './services/session.service';
import { Observable } from 'rxjs';
import { User } from './common/models/user';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'front';
  isLogged$!: Observable<boolean>; 
  currentUser$!: Observable<User | undefined>;

  constructor(private sessionService: SessionService) {}

  ngOnInit(): void {
    this.isLogged$ = this.sessionService.isLogged$;  
    this.currentUser$ = this.sessionService.user$;  
  }


  
}
