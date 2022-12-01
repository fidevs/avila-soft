import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {
  form = new FormGroup({
    username: new FormControl('', [Validators.required, Validators.maxLength(255)]),
    password: new FormControl('', [Validators.required, Validators.maxLength(50)])
  });
  loading = false;

  constructor(private auth: AuthService, private router: Router) { }

  ngOnInit() {
    this.auth.sessionIsValid(this.auth.currentSession) && this.goToHome();
  }

  ionViewWillEnter() {
    console.log("ionViewWillEnter")
    this.auth.sessionIsValid(this.auth.currentSession) && this.goToHome();
  }

  onSubmit() {
    this.loading = true;
    this.form.disable();
    this.auth.login(this.form.value.username, this.form.value.password).subscribe({
      next: () => {
        this.loading = false;
        this.form.reset();
        this.form.enable();
        this.goToHome();
      },
      error: () => {
        this.form.enable()
        this.loading = false;
      }
    });
  }

  goToHome() { this.router.navigateByUrl('/home'); }
}
