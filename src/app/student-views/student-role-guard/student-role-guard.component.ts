import {Injectable} from '@angular/core';
import {TokenStorageService} from "../../_services/token-storage.service";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";

@Injectable()
export class StudentRoleGuardComponent implements CanActivate {

  constructor(private userService:TokenStorageService, private router:Router) { }

  canActivate(next:ActivatedRouteSnapshot, state:RouterStateSnapshot) {
    if (this.userService.getCurrentUserValue().role === "STUDENT") {
      return true;
    }
    this.router.navigate(['/login']);
    return false;
  }

}
