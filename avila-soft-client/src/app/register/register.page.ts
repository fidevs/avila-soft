import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastController } from '@ionic/angular';
import { ApiService } from '../services/api.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.page.html',
  styleUrls: ['./register.page.scss'],
})
export class RegisterPage implements OnInit {
  form = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email, Validators.maxLength(255)]),
    user: new FormControl('', [Validators.required, Validators.maxLength(20)]),
    pswd: new FormControl('', [Validators.required, Validators.maxLength(50)]),
    pswdConfirm: new FormControl('', [Validators.required, Validators.maxLength(50)])
  });
  loading = false;

  constructor(public toastController: ToastController, private api: ApiService, private router: Router) { }

  ngOnInit() {
  }

  onSubmit() {
    const { email, user, pswd, pswdConfirm } = this.form.value;
    this.form.patchValue({ email: email.trim(), user: user.trim(), pswd: pswd.trim(), pswdConfirm: pswdConfirm.trim() });
    if (this.form.valid) {
      if (pswd !== pswdConfirm) { this.presentToast('', 'Las contraseñas no coinciden', 'danger', 3000); }
      else {
        this.loading = true;
        this.form.disable();
        this.api.saveAccount(email, user, pswd).subscribe({
          next: account => {
            this.presentToast('', `Se han enviadoc instrucciones para verificar la cuenta al correo ${account.email}`, 'success', 4000);
            this.form.reset();
            this.form.enable();
            this.loading = false;
            this.router.navigateByUrl('/');
          },
          error: () => {
            this.form.enable();
            this.loading = false;
          }
        });
      }
    } else { this.presentToast('Datos incorrectos', '', 'warning'); }
  }

  async presentToast(header: string, message: string, color: string, duration?: number) {
    const toast = await this.toastController.create({
      header, message, color, duration,
      buttons: [
        {
          icon: 'close-outline',
          role: 'cancel',
        }
      ],
      position: 'top'
    });
    toast.present();
  }

}
