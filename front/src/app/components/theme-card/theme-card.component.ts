import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Topic } from 'src/app/common/models/topic';

@Component({
  selector: 'app-theme-card',
  templateUrl: './theme-card.component.html',
  styleUrls: ['./theme-card.component.scss']
})
export class ThemeCardComponent implements OnInit {

  @Input() theme!: Topic;  
  @Input() isSubscribed!: boolean;  
  @Output() subscribe = new EventEmitter<number>(); 
  @Input() inProfile: boolean = false;
  @Output() unsubscribe = new EventEmitter<number>(); 

  constructor() { }

  ngOnInit(): void {
  }


  subscribeToTheme(): void {
    this.subscribe.emit(this.theme.id);  
  }
  handleSubscription(): void {
    if (this.inProfile && this.isSubscribed) {
      this.unsubscribe.emit(this.theme.id);
    } else {
      this.subscribe.emit(this.theme.id);
    }
  }

  
}
