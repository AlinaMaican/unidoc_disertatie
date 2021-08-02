import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

const AUTH_API = 'http://localhost:8088/unidoc/api/secretary/';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class SecretaryService {

  constructor(private http: HttpClient) { }

  getAllSecretaries(): Observable<any> {
    return this.http.get( AUTH_API + 'all', httpOptions);
  }

}



