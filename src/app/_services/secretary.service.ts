import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {SecretaryDocumentModel} from "../model/secretary-document.model";
import {SecretaryAllocationModel} from "../model/secretary-allocation.model";
import {StudentDocumentRowModel} from "../model/student-document-row.model";
import {StudentDocumentFilterModel} from "../model/student-document-filter.model";
import {PageModel} from "../model/page.model";
import {ResponseModel} from "../model/response.model";

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
    formData.append("id", document.get("id").value);
    formData.append("name", document.get("name").value);
    formData.append("description", document.get("description").value);
    formData.append("endDateOfUpload", new Date(document.get("endDateOfUpload").value).toISOString());
    return this.http.post(SECRETARY_API + "document/edit", formData);
  }

  downloadDocument(filePath: string): Observable<any> {
    return this.http.get(SECRETARY_API + "downloadPdfDocument", {responseType: "blob", params: {'filePath': filePath}});
  }

  uploadDocument(uploadForm: any, allocationId: number): Observable<ResponseModel> {
    const formData: FormData = new FormData();
    formData.append("file", uploadForm.get("file").value, uploadForm.get("file").value.name);
    formData.append("name", uploadForm.get("name").value);
    formData.append("description", uploadForm.get("description").value);
    formData.append("endDateOfUpload", uploadForm.get("endDateOfUpload").value.toISOString());
    formData.append("allocationId", allocationId.toString());
    return this.http.post<ResponseModel>(SECRETARY_API + 'document/upload', formData);
  }

  getStudentDocumentRowModel(filter: StudentDocumentFilterModel): Observable<PageModel<StudentDocumentRowModel>>{
    let params = new HttpParams();
    params = params.append("pageSize", String(filter.pageSize));
    params = params.append("pageNumber", String(filter.pageNumber));
    params = params.append("secretaryId", String(filter.secretaryId));
    params = params.append("sortDirection", String(filter.sortDirection));
    params = params.append("columnName", String(filter.columnName));
    params = params.append("learningTypeId", String(filter.learningTypeId));
    if(filter.universityStudiesId !== undefined) {
      params = params.append("universityStudyId", String(filter.universityStudiesId));
    }
    if(filter.studyProgramId !== undefined) {
      params = params.append("studyProgramId", String(filter.studyProgramId));
    }
    if(filter.domainId !== undefined) {
      params = params.append("domainId", String(filter.domainId));
    }
    if(filter.studyYearId !== undefined) {
      params = params.append("studyYearId", String(filter.studyYearId));
    }
    if(filter.studyGroupId !== undefined) {
      params = params.append("studyGroupId", String(filter.studyGroupId));
    }
    if(filter.status !== undefined) {
      params = params.append("status", String(filter.status));
    }
    if(filter.firstName !== undefined) {
      params = params.append("firstName", String(filter.firstName));
    }
    if(filter.lastName !== undefined) {
      params = params.append("lastName", String(filter.lastName));
    }
    if(filter.name !== undefined) {
      params = params.append("name", String(filter.name));
    }
    console.log(params)
    return this.http.get<PageModel<StudentDocumentRowModel>>(SECRETARY_API + 'allocation/student/documents', {params: params});
  }

  editStudentDocumentStatus(documentId:number, status: string, comment: string): Observable<ResponseModel>{
    const formData: FormData = new FormData();
    formData.append("status", status);
    if(comment !== null){
      formData.append("comment", comment);
    }
    return this.http.post<ResponseModel>(SECRETARY_API + 'allocation/student/document/' + documentId, formData);
  }
}



