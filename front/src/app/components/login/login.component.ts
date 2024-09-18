import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { AuthSuccess } from 'src/app/common/models/authSuccess';
import { UserResponse } from 'src/app/common/models/userResponse';
import { AuthService } from 'src/app/services/auth.service';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit, OnDestroy  {

  loginForm!: FormGroup;

  private loginSubscription!: Subscription;
  private userSubscription!: Subscription;


  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthService,
    private toastr: ToastrService,
    private sessionService: SessionService
  ) { }

  ngOnInit(): void {
    this.initForm();
  }

  initForm(): void {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  submit(): void {
    if (this.loginForm.valid) {
      this.loginSubscription = this.authService.login(this.loginForm.value).subscribe({
        next: (response: AuthSuccess) => {
          localStorage.setItem('token', response.token);
          this.toastr.success('Connexion réussie !', 'Succès');
          this.userSubscription = this.authService.me().subscribe((user: UserResponse) => {
            this.sessionService.logIn(user);
            this.router.navigate(['/article-feed']);
          });
        },
        error: (error) => {
          this.toastr.error('Erreur lors de la connexion. Veuillez réessayer.', 'Erreur');
        }
      });
    } else {
      this.toastr.warning('Le formulaire n\'est pas valide.', 'Avertissement');
    }
  }

  get formControls() {
    return this.loginForm.controls;
  }

  goBack(): void {
    this.router.navigate(['/']);
  }

  ngOnDestroy(): void {
    if (this.loginSubscription) {
      this.loginSubscription.unsubscribe();
    }
    if (this.userSubscription) {
      this.userSubscription.unsubscribe();
    }
  }
}
