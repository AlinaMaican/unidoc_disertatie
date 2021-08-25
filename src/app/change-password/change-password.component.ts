import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../_services/auth.service";
import {Router} from "@angular/router";
import {RegularExpressionUtil} from "../util/regular-expression.util";
import {MatSnackBar} from "@angular/material/snack-bar";

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
              private router: Router,
              private _snackBar: MatSnackBar) {
    if(!!this.user){
      this.email = JSON.parse(this.user)["email"];
    }
    this.changePasswordForm = new FormGroup({
      email: new FormControl(null, [Validators.required, Validators.pattern(RegularExpressionUtil.EMAIL)]),
      oldPassword: new FormControl(null),
      password: new FormControl(null, [Validators.required, Validators.pattern('(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&#])[A-Za-z\d$@$!%*?&#].{8,}')]),
      confirmPassword: new FormControl(null, [Validators.required])
    })
  }

  ngOnInit(): void {
  }

  changePassword(): void {
    this.submitted = true;
    if (this.changePasswordForm.valid) {
      const {email, oldPassword, password, confirmPassword} = this.changePasswordForm.value;

      this.authService.changePassword(email, oldPassword, password, confirmPassword).subscribe(
        response => {
          if(response.type === "ERROR"){
            this.openSnackBar(response.message, "Close", "errorSnackBar")
          } else {
            this.isChangePasswordFailed = false;
            this.router.navigate(["/login"]);
            this.openSnackBar(response.message, "Close", "successSnackBar");
          }
        },
        err => {
          this.errorMessage = err.error.message;
          this.isChangePasswordFailed = true;
        }
      );
    }
  }

  openSnackBar(message: string, action: string, aClass: string) {
    this._snackBar.open(message, action, {panelClass: [aClass]});
  }

  get f() {
    return this.changePasswordForm.controls;
  }
}
