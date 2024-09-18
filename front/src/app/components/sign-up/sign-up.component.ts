import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from 'src/app/services/auth.service';
import { Location } from '@angular/common';  
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.scss']
})
export class SignUpComponent implements OnInit, OnDestroy  {

  registerForm!: FormGroup; 

  private registerSubscription!: Subscription;

  constructor(private fb: FormBuilder, 
    private authService: AuthService,
    private toastr: ToastrService,  
    private router: Router,
    private location: Location ) { }

  ngOnInit(): void {
    this.initForm(); 
  }

  goBack(): void {
    this.location.back();  
  }

  initForm(): void {
    this.registerForm = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [
        Validators.required,
        Validators.minLength(8),
        Validators.pattern(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[\W_]).*$/)
      ]]
    });
  }

  submit(): void {
    if (this.registerForm.valid) {

      this.registerSubscription = this.authService.register(this.registerForm.value).subscribe({
        next: (response) => {
          this.toastr.success('Inscription réussie !', 'Succès');
          this.router.navigate(['/login']);
        },
        error: (error) => {
          this.toastr.error('Erreur lors de l\'inscription. Veuillez réessayer.', 'Erreur');
        },
        complete: () => {
        }
      });
    } else {
      this.toastr.warning('Le formulaire n\'est pas valide.', 'Avertissement');
    }
  }
  

  get formControls() {
    return this.registerForm.controls;
  }

  ngOnDestroy(): void {
    if (this.registerSubscription) {
      this.registerSubscription.unsubscribe();
    }
  }
}
