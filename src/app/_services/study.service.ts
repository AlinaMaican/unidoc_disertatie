import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {StudyModel} from "../model/study.model";

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
}
