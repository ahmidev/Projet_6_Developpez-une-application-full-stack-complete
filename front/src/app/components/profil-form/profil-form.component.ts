import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/app/common/models/user';
import { UserDto } from 'src/app/common/models/userDto';
import { UserResponse } from 'src/app/common/models/userResponse';

@Component({
  selector: 'app-profil-form',
  templateUrl: './profil-form.component.html',
  styleUrls: ['./profil-form.component.scss']
})
export class ProfilFormComponent implements OnInit {

  @Input() user!: UserResponse; 
  @Output() formSubmitted = new EventEmitter<UserDto>(); 
  profileForm!: FormGroup;

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
    this.initForm();
  }

  initForm(): void {
    this.profileForm = this.fb.group({
      userName: [this.user.userName || '', Validators.required],
      email: [this.user.email || '', [Validators.required, Validators.email]],
      password: ['', [
        Validators.minLength(8),
        Validators.pattern(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[\W_]).*$/)
      ]]
    });
  }

  onSubmit(): void {
    if (this.profileForm.valid) {
      const formData: UserDto = {
        userName: this.profileForm.get('username')?.value,
        email: this.profileForm.get('email')?.value
      };

      if (this.profileForm.get('password')?.value) {
        formData.password = this.profileForm.get('password')?.value;
      }

      this.formSubmitted.emit(formData);
    }
  }
}
