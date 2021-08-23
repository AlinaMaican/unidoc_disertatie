import {Component, OnInit} from '@angular/core';
import {AppUserAuthModel} from "../model/app-user-auth.model";
import {TokenStorageService} from "../_services/token-storage.service";
import {RoleType} from "../type/role.type";
import {Router} from "@angular/router";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  user: AppUserAuthModel | undefined;
  isAdmin: boolean = false;
  isStudent: boolean = false;
  isSecretary: boolean = false;

  constructor(private tokenService: TokenStorageService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.user = this.tokenService.getUser();
    switch (this.user.role) {
      case RoleType.ADMIN:
        this.isAdmin = true;
        break;
      case RoleType.STUDENT:
        this.isStudent = true;
        break;
      case RoleType.SECRETARY:
        this.isSecretary = true;
        break;
      default:
        this.isAdmin = true;
    }
  }

  logOut(): void{
    window.sessionStorage.clear();
    this.router.navigate(['/login'])
  }
}
