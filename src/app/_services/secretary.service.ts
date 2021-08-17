import {Injectable, LOCALE_ID} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {SecretaryDocumentModel} from "../model/secretary-document.model";
import {SecretaryAllocationModel} from "../model/secretary-allocation.model";
import {formatDate} from "@angular/common";

const SECRETARY_API = 'http://localhost:8088/unidoc/api/secretary/';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class SecretaryService {

  constructor(private http: HttpClient) { }

  getAllSecretaries(): Observable<any> {
    return this.http.get( SECRETARY_API + 'all', httpOptions);
  }

  getAllAllocationsBySecretaryId(id : number): Observable<SecretaryAllocationModel[]>{
    return this.http.get<SecretaryAllocationModel[]>(SECRETARY_API + id + "/allocations", httpOptions);
  }

  getSecretaryDocumentsByAllocationId(allocationId : number): Observable<SecretaryDocumentModel[]>{
    return this.http.get<SecretaryDocumentModel[]>(SECRETARY_API + "allocation/" + allocationId + "/documents", httpOptions);
  }

  editSecretaryDocument(document: any): Observable<any>{
    const formData: FormData = new FormData();
    console.log(document.get("endDateOfUpload").value)
    formData.append("id", document.get("id").value);
    formData.append("name", document.get("name").value);
    formData.append("description", document.get("description").value);
    formData.append("endDateOfUpload", new Date(document.get("endDateOfUpload").value).toISOString());
    return this.http.post(SECRETARY_API + "document/edit", formData);
  }

  downloadDocument(filePath: string): Observable<any> {
    return this.http.get(SECRETARY_API + "downloadPdfDocument", {responseType: "blob", params: {'filePath': filePath}});
  }

  uploadDocument(uploadForm: any, allocationId: number): Observable<any> {
    const formData: FormData = new FormData();
    formData.append("file", uploadForm.get("file").value, uploadForm.get("file").value.name);
    formData.append("name", uploadForm.get("name").value);
    formData.append("description", uploadForm.get("description").value);
    formData.append("endDateOfUpload", uploadForm.get("endDateOfUpload").value.toISOString());
    formData.append("allocationId", allocationId.toString());
    console.log(formData)
    return this.http.post(SECRETARY_API + 'document/upload', formData);
  }
}



