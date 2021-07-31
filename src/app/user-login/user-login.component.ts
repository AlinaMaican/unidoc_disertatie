import {Component, OnInit} from '@angular/core';
import {AuthService} from "../_services/auth.service";
import {TokenStorageService} from "../_services/token-storage.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {RegularExpressionUtil} from "../util/regular-expression.util";
import {Router} from "@angular/router";

@Component({
  selector: 'bg-user-login',
  templateUrl: './user-login.component.html',
  styleUrls: ['./user-login.component.scss']
})
export class UserLoginComponent implements OnInit {
  userForm: FormGroup;
  errorMessage = '';
  submitted = false;
  isLoggedIn = false;
  isLoginFailed = false;

  constructor(private authService: AuthService,
              private tokenStorage: TokenStorageService,
              private router: Router) {
    this.userForm = new FormGroup({
      email: new FormControl(null, [Validators.required, Validators.pattern(RegularExpressionUtil.EMAIL)]),
      password: new FormControl(null, [Validators.required])
    });
  }

  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      // this.roles = this.tokenStorage.getUser().roles;
    }
  }

  loginUser(): void {
    this.submitted = true;
    if (this.userForm.valid) {
      const {email, password} = this.userForm.value;

      this.authService.login(email, password).subscribe(
        data => {
          this.tokenStorage.saveToken(data.token);
          console.log(data);
          this.tokenStorage.saveUser(data);
          this.tokenStorage.setUser(this.tokenStorage.getUser());

          this.isLoginFailed = false;
          this.isLoggedIn = true;
          // this.roles = this.tokenStorage.getUser().roles;
          console.log(this.tokenStorage.getUser().active)
          if(this.tokenStorage.getUser().active){
            this.router.navigate(["/home"]);
          } else {
            this.router.navigate(["/change-password"]);
          }

        },
        err => {
          this.errorMessage = err.error.message;
          this.isLoginFailed = true;
        }
      );
    }
  }

  get f() {
    return this.userForm.controls;
  }
}