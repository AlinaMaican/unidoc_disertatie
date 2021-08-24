import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../_services/auth.service";
import {TokenStorageService} from "../_services/token-storage.service";
import {Router} from "@angular/router";
import {RegularExpressionUtil} from "../util/regular-expression.util";

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent implements OnInit {
  changePasswordForm: FormGroup;
  errorMessage = '';
  submitted = false;
  isLoggedIn = false;
  isChangePasswordFailed = false;
  email = '';
  user = window.sessionStorage.getItem("auth-user");

  constructor(private authService: AuthService,
              private router: Router) {
    if(!!this.user){
      this.email = JSON.parse(this.user)["email"];
    }
    this.changePasswordForm = new FormGroup({
      email: new FormControl(null, [Validators.required, Validators.pattern(RegularExpressionUtil.EMAIL)]),
      password: new FormControl(null, [Validators.required]),
      confirmpassword: new FormControl(null, [Validators.required])
    })
  }

  ngOnInit(): void {
  }

  changePassword(): void {
    this.submitted = true;
    if (this.changePasswordForm.valid) {
      const {email, password, confirmpassword} = this.changePasswordForm.value;

      this.authService.changePassword(email, password, confirmpassword).subscribe(
        data => {
          console.log(data);
          this.isChangePasswordFailed = false;
          this.router.navigate(["/login"]);
        },
        err => {
          this.errorMessage = err.error.message;
          this.isChangePasswordFailed = true;
        }
      );
    }
  }

  get f() {
    return this.changePasswordForm.controls;
  }
}
