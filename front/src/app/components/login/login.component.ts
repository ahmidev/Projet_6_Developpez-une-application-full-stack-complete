import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthSuccess } from 'src/app/common/models/authSuccess';
import { AuthService } from 'src/app/services/auth.service';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm!: FormGroup;

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
      console.log(this.loginForm.value);
      
      this.authService.login(this.loginForm.value).subscribe({
        next: (response:AuthSuccess) => {
          console.log(response);
          localStorage.setItem('token', response.token);
          this.toastr.success('Connexion réussie !', 'Succès');
          this.authService.me().subscribe((user: any) => {
            console.log('USER##',user);
            
            this.sessionService.logIn(user);
            //this.router.navigate(['/board']);
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
    this.router.navigate(['/']);  // Retour à la page précédente ou page d'accueil
  }
}
