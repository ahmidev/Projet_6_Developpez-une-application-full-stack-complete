import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/app/common/models/user';

@Component({
  selector: 'app-profil-form',
  templateUrl: './profil-form.component.html',
  styleUrls: ['./profil-form.component.scss']
})
export class ProfilFormComponent implements OnInit {

  @Input() user!: User; 
  @Output() formSubmitted = new EventEmitter<any>(); 
  profileForm!: FormGroup;

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
    this.initForm();
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

  onSubmit(): void {
    if (this.profileForm.valid) {
      const formData: any = {
        username: this.profileForm.get('username')?.value,
        email: this.profileForm.get('email')?.value
      };

      if (this.profileForm.get('password')?.value) {
        formData.password = this.profileForm.get('password')?.value;
      }

      this.formSubmitted.emit(formData);
    }
  }
}
