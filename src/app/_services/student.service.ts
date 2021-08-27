import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {RequiredDocumentRowModel} from "../model/required-document-row.model";
import {ResponseModel} from "../model/response.model";
import {PageModel} from "../model/page.model";
import {StudentDocumentModel} from "../model/student-document.model";
import {StudentNotificationModel} from "../model/student-notification.model";
import {StudentModel} from "../model/student.model";
import {StudentDocumentRowModel} from "../model/student-document-row.model";

const STUDENT_API = 'http://localhost:8088/unidoc/api/student/';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  constructor(private http: HttpClient) {
  }

  uploadDocument(uploadForm: any, data: any): Observable<ResponseModel> {
    const formData: FormData = new FormData();
    formData.append("file", uploadForm.get("file").value, uploadForm.get("file").value.name);
    formData.append("name", uploadForm.get("name").value);
    formData.append("studentId", data.studentId);
    formData.append("secretaryDocumentId", data.secretaryDocumentId);
    return this.http.post<ResponseModel>(STUDENT_API + 'document/secretary/upload', formData);
  }

  getRequiredDocuments(studentId: number): Observable<RequiredDocumentRowModel[]> {
    return this.http.get<RequiredDocumentRowModel[]>(STUDENT_API + 'document/secretary', {params: {'studentId': studentId}});
  }

  getOwnDocuments(studentId: number, pageSize: number, pageNumber: number): Observable<PageModel<StudentDocumentModel>>{
    let params = new HttpParams();
    params = params.append("pageSize", String(pageSize));
    params = params.append("pageNumber", String(pageNumber));
    params = params.append("studentId", String(studentId));
    return this.http.get<PageModel<StudentDocumentModel>>(STUDENT_API + 'document/own', {params: params});
  }

  uploadOwnDocument(uploadForm: any, data: any): Observable<ResponseModel> {
    const formData: FormData = new FormData();
    formData.append("file", uploadForm.get("file").value, uploadForm.get("file").value.name);
    formData.append("name", uploadForm.get("name").value);
    formData.append("studentId", data.studentId);
    formData.append("description", uploadForm.get("description").value);
    return this.http.post<ResponseModel>(STUDENT_API + 'document/own/upload', formData);
  }

  getStudentNotifications(studentId: number, pageSize: number, pageNumber: number, columnName: string, direction:string): Observable<PageModel<StudentNotificationModel>>{
    let params = new HttpParams();
    params = params.append("pageSize", String(pageSize));
    params = params.append("pageNumber", String(pageNumber));
    params = params.append("studentId", String(studentId));
    params = params.append("columnName", String(columnName));
    params = params.append("direction", String(direction));
    return this.http.get<PageModel<StudentNotificationModel>>(STUDENT_API + 'document/notifications', {params: params});
  }

  checkNotification(notificationId: number): Observable<ResponseModel>{
    const formData: FormData = new FormData();
    formData.append("notificationId", String(notificationId));
    return this.http.post<ResponseModel>(STUDENT_API + "notification/seen", formData);
  }

  getUnseenNotifications(userId: number): Observable<number>{
    let params = new HttpParams();
    params = params.append("userId", String(userId));
    return this.http.get<number>(STUDENT_API + "notifications/unseen", {params: params});
  }

  deleteOwnDocument(documentId: number): Observable<ResponseModel> {
    return this.http.delete<ResponseModel>(STUDENT_API + 'document/own/delete/' + documentId, httpOptions);
  }

  getAllStudents(pageSize: number, pageNumber: number): Observable<PageModel<StudentModel>>{
    let params = new HttpParams();
    params = params.append("pageSize", String(pageSize));
    params = params.append("pageNumber", String(pageNumber));
    return this.http.get<PageModel<StudentModel>>(STUDENT_API + 'all', {params: params});
  }
}
