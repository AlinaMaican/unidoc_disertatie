import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

const AUTH_API = 'http://localhost:8088/unidoc/api/auth/';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) {
  }

  login(email: string, password: string): Observable<any> {
    return this.http.post(AUTH_API + 'login', {email, password}, httpOptions);
  }

  changePassword(email: string, oldPassword: string, password: string, confirmPassword: string): Observable<any> {
    return this.http.post(AUTH_API + 'change_password', {email, oldPassword, password, confirmPassword}, httpOptions);
  }
}