import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {StudyModel} from "../model/study.model";
import {ResponseModel} from "../model/response.model";

const STUDY_API = 'http://localhost:8088/unidoc/api/study/';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
  params: {}
};
@Injectable({
  providedIn: 'root'
})
export class StudyService {

  constructor(private http: HttpClient) { }

  getAllFilteredLearningTypes(secretaryId: number): Observable<StudyModel[]> {
    httpOptions.params = {'secretaryId': secretaryId};
    return this.http.get<StudyModel[]>( STUDY_API + 'filteredLearningTypes', httpOptions);
  }

  getAllFilteredUniversityStudies(secretaryId: number, learningTypeId: number): Observable<StudyModel[]> {
    httpOptions.params = {'secretaryId': secretaryId,
                          'learningTypeId': learningTypeId};
    return this.http.get<StudyModel[]>( STUDY_API + 'filteredUniversityStudyTypes', httpOptions);
  }

  getAllFilteredDomains(secretaryId: number, universityStudyId: number): Observable<StudyModel[]> {
    httpOptions.params = {'secretaryId': secretaryId,
                          'universityStudyId': universityStudyId};
    return this.http.get<StudyModel[]>( STUDY_API + 'filteredDomains', httpOptions);
  }

  getAllFilteredStudyPrograms(secretaryId: number, domainId: number): Observable<StudyModel[]> {
    httpOptions.params = {'secretaryId': secretaryId,
                          'domainId': domainId};
    return this.http.get<StudyModel[]>( STUDY_API + 'filteredStudyPrograms', httpOptions);
  }

  getAllFilteredStudyYears(secretaryId: number, studyProgramId: number): Observable<StudyModel[]> {
    httpOptions.params = {'secretaryId': secretaryId,
                          'studyProgramId': studyProgramId};
    return this.http.get<StudyModel[]>( STUDY_API + 'filteredStudyYears', httpOptions);
  }

  getAllFilteredStudyGroups(allocationId: number): Observable<StudyModel[]> {
    httpOptions.params = {'allocationId': allocationId};
    return this.http.get<StudyModel[]>( STUDY_API + 'studyGroups', httpOptions);
  }

  getAllLearningTypes(): Observable<StudyModel[]> {
    return this.http.get<StudyModel[]>( STUDY_API + 'learningTypes', httpOptions);
  }

  getAllUniversityStudies(learningTypeId: number): Observable<StudyModel[]> {
    httpOptions.params = {'learningTypeId': learningTypeId};
    return this.http.get<StudyModel[]>( STUDY_API + 'universityStudyTypes', httpOptions);
  }

  getAllDomains(universityStudyId: number): Observable<StudyModel[]> {
    httpOptions.params = {'universityStudyId': universityStudyId};
    return this.http.get<StudyModel[]>( STUDY_API + 'domains', httpOptions);
  }

  getAllStudyPrograms(domainId: number): Observable<StudyModel[]> {
    httpOptions.params = {'domainId': domainId};
    return this.http.get<StudyModel[]>( STUDY_API + 'studyPrograms', httpOptions);
  }

  getAllStudyYears(studyProgramId: number): Observable<StudyModel[]> {
    httpOptions.params = {'studyProgramId': studyProgramId};
    return this.http.get<StudyModel[]>( STUDY_API + 'studyYears', httpOptions);
  }

  createAllocation(allocation: any): Observable<ResponseModel>{
    return this.http.post<ResponseModel>(STUDY_API + 'allocation/create', allocation);
  }

  deleteAllocation(allocationId: number): Observable<ResponseModel> {
    return this.http.delete<ResponseModel>(STUDY_API + 'allocation/delete/' + allocationId, httpOptions);
  }

  getAllocationFilter(secretaryId: number): Observable<StudyModel[]>{
    httpOptions.params = {'secretaryId': secretaryId};
    return this.http.get<StudyModel[]>(STUDY_API + 'allocationFilter' , httpOptions);
  }

  getAllowedLearningTypes(secretaryId: number): Observable<StudyModel[]> {
    httpOptions.params = {'secretaryId': secretaryId};
    return this.http.get<StudyModel[]>( STUDY_API + 'allowedLearningTypes', httpOptions);
  }

  getAllowedUniversityStudies(secretaryId: number, learningTypeId: string): Observable<StudyModel[]> {
    httpOptions.params = {'secretaryId': secretaryId,
      'learningType': learningTypeId};
    return this.http.get<StudyModel[]>( STUDY_API + 'allowedUniversityStudies', httpOptions);
  }

  getAllowedDomains(secretaryId: number, learningTypeId: string, universityStudyId: string): Observable<StudyModel[]> {
    httpOptions.params = {'secretaryId': secretaryId,
      'learningType': learningTypeId,
      'universityStudy': universityStudyId};
    return this.http.get<StudyModel[]>( STUDY_API + 'allowedDomains', httpOptions);
  }

  getAllowedStudyPrograms(secretaryId: number, learningTypeId: string, universityStudyId: string, domainId: string): Observable<StudyModel[]> {
    httpOptions.params = {'secretaryId': secretaryId,
      'learningType': learningTypeId,
      'universityStudy': universityStudyId,
      'domain': domainId};
    return this.http.get<StudyModel[]>( STUDY_API + 'allowedStudyPrograms', httpOptions);
  }

  getAllowedStudyYears(secretaryId: number, learningTypeId: string, universityStudyId: string, domainId: string, studyProgramId: string): Observable<StudyModel[]> {
    httpOptions.params = {'secretaryId': secretaryId,
      'learningType': learningTypeId,
      'universityStudy': universityStudyId,
      'domain': domainId,
      'studyProgram': studyProgramId};
    return this.http.get<StudyModel[]>( STUDY_API + 'allowedStudyYears', httpOptions);
  }
}
