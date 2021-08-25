import {Component, OnInit} from '@angular/core';
import {AuthService} from "../_services/auth.service";
import {TokenStorageService} from "../_services/token-storage.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {RegularExpressionUtil} from "../util/regular-expression.util";
import {Router} from "@angular/router";
import {UserService} from "../_services/user.service";
import {MatSnackBar} from "@angular/material/snack-bar";

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
  hide = true;

  constructor(private authService: AuthService,
              private tokenStorage: TokenStorageService,
              private userService: UserService,
              private router: Router) {
    this.userForm = new FormGroup({
      email: new FormControl(null, [Validators.required, Validators.pattern(RegularExpressionUtil.EMAIL)]),
      password: new FormControl(null, [Validators.required])
    });
  }

  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
    }
  }

  loginUser(): void {
    this.submitted = true;
    if (this.userForm.valid) {
      const {email, password} = this.userForm.value;

      this.authService.login(email, password).subscribe(
        data => {
          if(data.type !== undefined && data.type === "ERROR"){
              this.isLoginFailed = true;
              this.errorMessage = data.message;
          } else {
            this.tokenStorage.saveToken(data.token);
            this.tokenStorage.saveUser(data);
            this.tokenStorage.setUser(this.tokenStorage.getUser());

            this.isLoginFailed = false;
            this.isLoggedIn = true;

            if (this.tokenStorage.getUser().active) {
              if (this.tokenStorage.getUser().role === "STUDENT") {
                this.userService.getStudent(this.tokenStorage.getUser().id).subscribe(
                  data => {
                    window.sessionStorage.setItem("student", JSON.stringify(data));
                    this.router.navigate(["/required-documents"]);
                  }
                );
              } else if (this.tokenStorage.getUser().role === "SECRETARY") {
                this.userService.getSecretary(this.tokenStorage.getUser().id).subscribe(
                  data => {
                    window.sessionStorage.setItem("secretary", JSON.stringify(data));
                    this.router.navigate(["/document-management"]);
                  }
                );
              } else {
                this.router.navigate(["/secretary-management"]);
              }
            } else {
              this.router.navigate(["/change-password"]);
            }
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
