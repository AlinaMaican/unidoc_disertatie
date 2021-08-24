import {Component, OnInit} from '@angular/core';
import {AppUserAuthModel} from "../model/app-user-auth.model";
import {TokenStorageService} from "../_services/token-storage.service";
import {RoleType} from "../type/role.type";
import {Router} from "@angular/router";
import {StudentService} from "../_services/student.service";
import {SecretaryService} from "../_services/secretary.service";

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

  matBadgeCount: number = 0;
  hideMatBadge: boolean = true;
  studentInterval: any;
  secretaryInterval: any;


  constructor(private tokenService: TokenStorageService,
              private router: Router,
              private studentService: StudentService,
              private secretaryService: SecretaryService) {
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
    if(this.isStudent){
      this.studentService.getUnseenNotifications(this.user.id).subscribe(number=>{
        this.matBadgeCount = number;
        this.hideMatBadge = this.matBadgeCount === 0;
      });
      this.studentInterval = setInterval(() => {
        // @ts-ignore
        this.studentService.getUnseenNotifications(this.user.id).subscribe(number=>{
          this.matBadgeCount = number;
          this.hideMatBadge = this.matBadgeCount === 0;
        });
      }, 5000);
    }
    if(this.isSecretary){
      this.secretaryService.getUnseenNotifications(this.user.id).subscribe(number=>{
        this.matBadgeCount = number;
        this.hideMatBadge = this.matBadgeCount === 0;
      });
      this.secretaryInterval = setInterval(() => {
        // @ts-ignore
        this.secretaryService.getUnseenNotifications(this.user.id).subscribe(number=>{
          this.matBadgeCount = number;
          this.hideMatBadge = this.matBadgeCount === 0;
        });
      }, 5000);
    }
  }

  logOut(): void{
    window.sessionStorage.clear();
    clearInterval(this.secretaryInterval);
    clearInterval(this.studentInterval);
    this.router.navigate(['/login'])
  }
}
