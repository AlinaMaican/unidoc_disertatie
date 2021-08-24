import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

const USER_API = 'http://localhost:8088/unidoc/api/user/';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getStudent(userId: number): Observable<any>{
    return this.http.get(USER_API + "student", {headers: new HttpHeaders({'Content-Type': 'application/json'})
      , params: {'userId': userId}});
  }

  getSecretary(userId: number): Observable<any>{
    return this.http.get(USER_API + "secretary", {headers: new HttpHeaders({'Content-Type': 'application/json'})
      , params: {'userId': userId}});
  }

  getUserProfile(userId: number): Observable<any>{
    return this.http.get(USER_API + "user-profile", {headers: new HttpHeaders({'Content-Type': 'application/json'})
      , params: {'userId': userId}});
  }
}
