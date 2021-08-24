import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {TokenStorageService} from "../../_services/token-storage.service";

@Injectable()
export class RoleGuardComponent implements CanActivate {

  constructor(private userService:TokenStorageService, private router:Router) {
  }

  canActivate(next:ActivatedRouteSnapshot, state:RouterStateSnapshot) {
    if (this.userService.getCurrentUserValue().role === "ADMIN") {
      return true;
    }
    this.router.navigate(['/login']);
    return false;
  }
}

