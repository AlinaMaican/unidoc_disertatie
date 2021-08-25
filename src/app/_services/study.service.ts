import { Injectable } from '@angular/core';
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

  getAllFilteredStudyGroups(studyYearId: number): Observable<StudyModel[]> {
    httpOptions.params = {'studyYearId': studyYearId};
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
}
