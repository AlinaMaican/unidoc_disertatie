import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {StudyModel} from "../model/study.model";
import {StudyService} from "../_services/study.service";
import {SecretaryDocumentModel} from "../model/secretary-document.model";
import {SecretaryService} from "../_services/secretary.service";
import {StudentDocumentRowModel} from "../model/student-document-row.model";
import {MatTableDataSource} from "@angular/material/table";
import {MatSort} from "@angular/material/sort";
import {MatPaginator} from "@angular/material/paginator";
import {PageModel} from "../model/page.model";

@Component({
  selector: 'app-student-documents',
  templateUrl: './student-documents.component.html',
  styleUrls: ['./student-documents.component.scss']
})
export class StudentDocumentsComponent implements OnInit, AfterViewInit {
  displayedColumns: string[] = ['studyGroup', 'firstName', 'lastName', 'fileName', 'dateOfUpload', 'status', 'viewDocument'];
  displayedColumnFilters: string[] = ['studyGroup-filter', 'firstName-filter', 'lastName-filter', 'fileName-filter', 'dateOfUpload-filter', 'status-filter', 'viewDocument-filter'];
  clickedRows = new Set<SecretaryDocumentModel>();
  dataSource = new MatTableDataSource<StudentDocumentRowModel>();
  learningTypes: StudyModel[] = [];
  universityStudies: StudyModel[] = [];
  domains: StudyModel[] = [];
  studyPrograms: StudyModel[] = [];
  studyYears: StudyModel[] = [];
  secretary: any;

  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;
  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private studyService: StudyService,
              private secretaryService: SecretaryService) {
  }

  ngOnInit(): void {
    if (window.sessionStorage.getItem("secretary") !== null) {
      this.secretary = JSON.parse(<string>window.sessionStorage.getItem("secretary"));
      this.studyService.getAllFilteredLearningTypes(this.secretary.id).subscribe(data => {
        this.learningTypes = data;
      })
    }
    this.secretaryService.getStudentDocumentRowModel().subscribe(data => {
      console.log(data.content);
      this.dataSource = new MatTableDataSource(data.content);
      })
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  makeUniversityStudyVisible(learningType: number): void {
    let element = document.getElementById("universityStudyId");
    if(element !== null){
      element.classList.remove("hidden-element");
      this.studyService.getAllFilteredUniversityStudies(this.secretary.id, learningType).subscribe(data => {
        this.universityStudies = data;
      })
    }
  }

  makeDomainVisible(universityStudy: number): void {
    let element = document.getElementById("domainId");
    if(element !== null){
      element.classList.remove("hidden-element");
      this.studyService.getAllFilteredDomains(this.secretary.id, universityStudy).subscribe(data => {
        this.domains = data;
      })
    }
  }

  makeStudyProgramVisible(domain: number): void {
    let element = document.getElementById("studyProgramId");
    if(element !== null){
      element.classList.remove("hidden-element");
      this.studyService.getAllFilteredStudyPrograms(this.secretary.id, domain).subscribe(data => {
        this.studyPrograms = data;
      })
    }
  }

  makeStudyYearVisible(studyProgram: number): void {
    let element = document.getElementById("studyYearId");
    if(element !== null){
      element.classList.remove("hidden-element");
      this.studyService.getAllFilteredStudyYears(this.secretary.id, studyProgram).subscribe(data => {
        this.studyYears = data;
      })
    }
  }

  openPdf(filePath: string): void{
    this.secretaryService.downloadDocument(filePath).subscribe(data => {
      let file = new Blob([data], {type: 'application/pdf'});
      const fileURL = URL.createObjectURL(file);
      window.open(fileURL, '_blank');
    });
  }
}
