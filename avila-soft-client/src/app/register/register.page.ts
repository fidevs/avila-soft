import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ToastController } from '@ionic/angular';

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

  constructor(public toastController: ToastController) { }

  ngOnInit() {
  }

  onSubmit() {
    const { email, user, pswd, pswdConfirm } = this.form.value;
    this.form.patchValue({ email: email.trim(), user: user.trim(), pswd: pswd.trim(), pswdConfirm: pswdConfirm.trim() });
    if (this.form.valid) {
      if (pswd !== pswdConfirm) { this.presentToast('', 'Las contraseñas no coinciden', 'danger', 3000); }
      else {
        this.presentToast('', 'Datos correctos!', 'success', 3000);
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
