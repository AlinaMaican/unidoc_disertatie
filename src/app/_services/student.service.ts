import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {RequiredDocumentRowModel} from "../model/required-document-row.model";
import {ResponseModel} from "../model/response.model";

const STUDENT_API = 'http://localhost:8088/unidoc/api/student/';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  constructor(private http:HttpClient) { }

  uploadDocument(uploadForm: any, data: any): Observable<ResponseModel> {
    const formData: FormData = new FormData();
    formData.append("file", uploadForm.get("file").value, uploadForm.get("file").value.name);
    formData.append("name", uploadForm.get("name").value);
    formData.append("studentId", data.studentId);
    formData.append("secretaryDocumentId", data.secretaryDocumentId);
    return this.http.post<ResponseModel>(STUDENT_API + 'document/secretary/upload', formData);
  }

  getRequiredDocuments(studentId: number):Observable<RequiredDocumentRowModel[]>{
    return this.http.get<RequiredDocumentRowModel[]>(STUDENT_API + 'secretary/document', {params: {'studentId': studentId}});
}

}
