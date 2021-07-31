import {Injectable} from '@angular/core';
import {AppUserAuthModel} from "../model/app-user-auth.model";
import {JwtHelperService} from "@auth0/angular-jwt";
import {RoleType} from "../type/role.type";
import {BehaviorSubject, Observable} from "rxjs";

const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {
  private currentUserSubject: BehaviorSubject<AppUserAuthModel>;
  public currentUser: Observable<AppUserAuthModel>;

  constructor() {
    this.currentUserSubject = new BehaviorSubject<AppUserAuthModel>(this.getUser());
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public saveToken(token: string): void {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string | null {
    return window.sessionStorage.getItem(TOKEN_KEY);
  }

  public saveUser(user: any): void {
    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  public getUser(): AppUserAuthModel {
    const user = window.sessionStorage.getItem(USER_KEY);

    if(!!user) {
      const decodedUser = JSON.parse(user);
      return <AppUserAuthModel>{
        active: decodedUser["active"],
        email: decodedUser["email"],
        role: this.getRole(decodedUser["role"])
      };
    }
    return <AppUserAuthModel>{
      email: "",
      active: false,
      role: RoleType.ADMIN
    };
  }

  getRole(role: string) {
    if (role === RoleType.STUDENT) {
      return RoleType.STUDENT
    }

    if (role === RoleType.ADMIN) {
      return RoleType.ADMIN
    }

    if (role === RoleType.SECRETARY) {
      return RoleType.SECRETARY
    }

    return null;
  }

  public get currentUserValue(): AppUserAuthModel {
    return this.currentUserSubject.value;
  }

  setUser(user: AppUserAuthModel) {
    this.currentUserSubject.next(user);
  }
}
