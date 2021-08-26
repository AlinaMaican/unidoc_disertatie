import { Component, OnInit } from '@angular/core';
import {SecretaryDocumentModel} from "../../model/secretary-document.model";
import {MatTableDataSource} from "@angular/material/table";
import {StudentDocumentRowModel} from "../../model/student-document-row.model";
import {StudyModel} from "../../model/study.model";
import {StudyService} from "../../_services/study.service";
import {SecretaryService} from "../../_services/secretary.service";
import {MatDialog} from "@angular/material/dialog";
import {StudentDocumentFilterModel} from "../../model/student-document-filter.model";
import {StudentModel} from "../../model/student.model";
import {StudentDetailsDialogComponent} from "../student-details-dialog/student-details-dialog.component";
import {ChangeStatusStudentDocumentDialogComponent} from "../change-status-student-document-dialog/change-status-student-document-dialog.component";
import {allowMangle} from "@angular-devkit/build-angular/src/utils/environment-options";

@Component({
  selector: 'app-secretary-notifications',
  templateUrl: './secretary-notifications.component.html',
  styleUrls: ['./secretary-notifications.component.scss']
})
export class SecretaryNotificationsComponent implements OnInit {
  displayedColumns: string[] = ['studyGroup', 'firstName', 'lastName', 'name', 'description', 'dateOfUpload', 'status', 'viewDocument'];
  displayedColumnFilters: string[] = ['studyGroup-filter', 'firstName-filter', 'lastName-filter', 'name-filter', 'description-filter','dateOfUpload-filter', 'status-filter', 'viewDocument-filter'];
  clickedRows = new Set<SecretaryDocumentModel>();
  dataSource = new MatTableDataSource<StudentDocumentRowModel>();
  allocations: StudyModel[] = [];
  // learningTypes: StudyModel[] = [];
  // universityStudies: StudyModel[] = [];
  // domains: StudyModel[] = [];
  // studyPrograms: StudyModel[] = [];
  // studyYears: StudyModel[] = [];
  studyGroups: StudyModel[] = [];
  secretary: any;
  inProgress = 'IN_PROGRESS';
  valid = 'VALID';
  invalid = 'INVALID';
  allStatuses = 'ALL';
  selectLearningType: any;
  selectUniversityStudy: any;
  selectDomain: any;
  selectStudyProgram: any;
  selectStudyYear: any;
  selectStudyGroup: any;
  selectStatus: any;
  selectAllocation: any;
  firstName: any;
  lastName: any;
  fileName: any;
  tooltipText = "Click here to see the student's details";
  changeStatusTooltipMessage = "Change the status of the document and notify the student about this change!";
  viewDocumentTooltipMessage= "Click here to see the document";

  dataLength:any;
  pageIndex:number = 0;
  pageSize:number = 10;
  pageSizeOptions:number[] = [1, 5, 10];

  columnName = 'dateOfUpload';
  direction = 'desc';

  constructor(private studyService: StudyService,
              private secretaryService: SecretaryService,
              public dialog: MatDialog) { }

  ngOnInit(): void {
    if (window.sessionStorage.getItem("secretary") !== null) {
      this.secretary = JSON.parse(<string>window.sessionStorage.getItem("secretary"));
      this.studyService.getAllocationFilter(this.secretary.id).subscribe(data => {
        this.allocations = data;
      })
    }
  }

  // makeUniversityStudyVisible(learningType: number): void {
  //   let table = document.getElementById("table");
  //   let tablePagination = document.getElementById("tablePagination");
  //   let info = document.getElementsByTagName("h3")[0];
  //   if (table !== null && tablePagination !== null && info !== null) {
  //     info.classList.add("hidden-element");
  //     table.classList.remove("hidden-element");
  //     tablePagination.classList.remove("hidden-element");
  //     this.filterTable();
  //     let element = document.getElementById("universityStudyId");
  //     if (element !== null) {
  //       element.classList.remove("hidden-element");
  //       this.studyService.getAllFilteredUniversityStudies(this.secretary.id, learningType).subscribe(data => {
  //         this.universityStudies = data;
  //       })
  //     }
  //   }
  // }
  //
  // makeDomainVisible(universityStudy: number): void {
  //   let element = document.getElementById("domainId");
  //   if (element !== null) {
  //     this.filterTable();
  //     element.classList.remove("hidden-element");
  //     this.studyService.getAllFilteredDomains(this.secretary.id, universityStudy).subscribe(data => {
  //       this.domains = data;
  //     })
  //   }
  // }
  //
  // makeStudyProgramVisible(domain: number): void {
  //   let element = document.getElementById("studyProgramId");
  //   if (element !== null) {
  //     this.filterTable();
  //     element.classList.remove("hidden-element");
  //     this.studyService.getAllFilteredStudyPrograms(this.secretary.id, domain).subscribe(data => {
  //       this.studyPrograms = data;
  //     })
  //   }
  // }
  //
  // makeStudyYearVisible(studyProgram: number): void {
  //   let element = document.getElementById("studyYearId");
  //   if (element !== null) {
  //     this.filterTable();
  //     element.classList.remove("hidden-element");
  //     this.studyService.getAllFilteredStudyYears(this.secretary.id, studyProgram).subscribe(data => {
  //       this.studyYears = data;
  //     })
  //   }
  // }

  makeStudyGroupVisible(allocation: number): void {
    let element = document.getElementById("studyGroupId");
    let table = document.getElementById("table");
    let tablePagination = document.getElementById("tablePagination");
    let info = document.getElementsByTagName("h3")[0];
    if (table !== null && tablePagination !== null && info !== null) {
      info.classList.add("hidden-element");
      table.classList.remove("hidden-element");
      tablePagination.classList.remove("hidden-element");
      this.filterTable();
    }
    if (element !== null) {
      element.classList.remove("hidden-element");
      this.studyService.getAllFilteredStudyGroups(allocation).subscribe(data => {
        this.studyGroups = data;

        // @ts-ignore
        let all: StudyModel = {};
        all.id = -1;
        all.value = 'ALL';
        this.studyGroups = data;
        this.studyGroups.unshift(all);
      })
    }
  }

  openPdf(filePath: string, notificationId: number): void {
    this.secretaryService.downloadDocument(filePath).subscribe(data => {
      let file = new Blob([data], {type: 'application/pdf'});
      const fileURL = URL.createObjectURL(file);
      window.open(fileURL, '_blank');
    });
    this.secretaryService.checkNotification(notificationId).subscribe(()=>{

    });
  }

  filterTable(): StudentDocumentFilterModel {
    let filter: StudentDocumentFilterModel = {};
    // @ts-ignore
    // filter.learningTypeId = this.selectLearningType;
    // filter.universityStudiesId = this.selectUniversityStudy;
    // filter.domainId = this.selectDomain;
    // filter.studyProgramId = this.selectStudyProgram;
    // filter.studyYearId = this.selectStudyYear;
    filter.allocationId = this.selectAllocation;
    filter.studyGroupId = this.selectStudyGroup;
    filter.status = this.selectStatus;
    filter.secretaryId = this.secretary.id;
    filter.firstName = this.firstName;
    filter.lastName = this.lastName;
    filter.name = this.fileName;
    filter.pageNumber = this.pageIndex;
    filter.pageSize = this.pageSize;
    filter.columnName = this.columnName;
    filter.sortDirection = this.direction;
    this.secretaryService.getOwnStudentDocumentRowModel(filter).subscribe(data => {
      this.dataSource = new MatTableDataSource(data.content);
      this.setPagination(data.totalPages, data.pageable.pageNumber, data.pageable.pageSize)
    })
    return filter;
  }

  setPagination(length: number, startIndex: number, pageSize: number) {
    this.dataLength = length;
    this.pageIndex = startIndex;
    this.pageSize = pageSize;
  }

  onPaginationChange(event: any) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.filterTable();
  }

  openDetailsDialog(studentDetails: StudentModel): void{
    const dialogRef = this.dialog.open(StudentDetailsDialogComponent, {
      width: '500px',
      data: studentDetails
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  openStatusChangeDialog(document: StudentDocumentRowModel): void{
    const dialogRef = this.dialog.open(ChangeStatusStudentDocumentDialogComponent, {
      width: '500px',
      data: document
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  sortData(event: any){
    this.columnName = event['active'];
    this.direction = event['direction'];
    this.filterTable();
  }
}

