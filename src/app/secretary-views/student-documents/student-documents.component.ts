import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {StudyModel} from "../../model/study.model";
import {StudyService} from "../../_services/study.service";
import {SecretaryDocumentModel} from "../../model/secretary-document.model";
import {SecretaryService} from "../../_services/secretary.service";
import {StudentDocumentRowModel} from "../../model/student-document-row.model";
import {MatTableDataSource} from "@angular/material/table";
import {MatSort} from "@angular/material/sort";
import {MatPaginator} from "@angular/material/paginator";
import {StudentDocumentFilterModel} from "../../model/student-document-filter.model";
import {StudentModel} from "../../model/student.model";
import {StudentDetailsDialogComponent} from "../student-details-dialog/student-details-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {ChangeStatusStudentDocumentDialogComponent} from "../change-status-student-document-dialog/change-status-student-document-dialog.component";

@Component({
  selector: 'app-student-documents',
  templateUrl: './student-documents.component.html',
  styleUrls: ['./student-documents.component.scss']
})
export class StudentDocumentsComponent implements OnInit, AfterViewInit {
  displayedColumns: string[] = ['studyGroup', 'firstName', 'lastName', 'name', 'dateOfUpload', 'status', 'viewDocument'];
  displayedColumnFilters: string[] = ['studyGroup-filter', 'firstName-filter', 'lastName-filter', 'name-filter', 'dateOfUpload-filter', 'status-filter', 'viewDocument-filter'];
  clickedRows = new Set<SecretaryDocumentModel>();
  dataSource = new MatTableDataSource<StudentDocumentRowModel>();
  learningTypes: StudyModel[] = [];
  universityStudies: StudyModel[] = [];
  domains: StudyModel[] = [];
  studyPrograms: StudyModel[] = [];
  studyYears: StudyModel[] = [];
  studyGroups: StudyModel[] = [];
  secretary: any;
  inProgress = 'IN_PROGRESS';
  valid = 'VALID';
  invalid = 'INVALID';
  selectLearningType: any;
  selectUniversityStudy: any;
  selectDomain: any;
  selectStudyProgram: any;
  selectStudyYear: any;
  selectStudyGroup: any;
  selectStatus: any;
  firstName: any;
  lastName: any;
  fileName: any;
  dataLength: any;
  tooltipText = "Click here to see the student's details";
  changeStatusTooltipMessage = "Change the status of the document and notify the student about this change!";
  viewDocumentTooltipMessage= "Click here to see the document";

  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;
  // @ts-ignore
  @ViewChild(MatPaginator, {static: false}) paginator : MatPaginator;

  constructor(private studyService: StudyService,
              private secretaryService: SecretaryService,
              public dialog: MatDialog) {
  }

  ngOnInit(): void {
    if (window.sessionStorage.getItem("secretary") !== null) {
      this.secretary = JSON.parse(<string>window.sessionStorage.getItem("secretary"));
      this.studyService.getAllFilteredLearningTypes(this.secretary.id).subscribe(data => {
        this.learningTypes = data;
      })
    }
  }

  ngAfterViewInit(): void {
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    this.dataSource.paginator = this.paginator;

    // merge(this.sort.sortChange, this.paginator.page)
    //   .pipe(startWith({}), switchMap(() => {
    //     console.log("caca")
    //       return this.secretaryService.getStudentDocumentRowModel(this.filterTable());}),
    //     map(data => {
    //       // Flip flag to show that loading has finished.
    //       if (data === null) {
    //         return [];
    //       }
    //
    //       // Only refresh the result length if there is new data. In case of rate
    //       // limit errors, we do not want to reset the paginator to zero, as that
    //       // would prevent users from re-triggering requests.
    //       this.dataLength = data.size;
    //       return data.content;
    //     })
    //   ).subscribe(data => this.dataSource = data);
  }

  makeUniversityStudyVisible(learningType: number): void {
    let table = document.getElementById("table");
    let tablePagination = document.getElementById("tablePagination");
    let info = document.getElementsByTagName("h3")[0];
    if (table !== null && tablePagination !== null && info !== null) {
      info.classList.add("hidden-element");
      table.classList.remove("hidden-element");
      tablePagination.classList.remove("hidden-element");
      this.filterTable();
      let element = document.getElementById("universityStudyId");
      if (element !== null) {
        element.classList.remove("hidden-element");
        this.studyService.getAllFilteredUniversityStudies(this.secretary.id, learningType).subscribe(data => {
          this.universityStudies = data;
        })
      }
    }
  }

  makeDomainVisible(universityStudy: number): void {
    let element = document.getElementById("domainId");
    if (element !== null) {
      this.filterTable();
      element.classList.remove("hidden-element");
      this.studyService.getAllFilteredDomains(this.secretary.id, universityStudy).subscribe(data => {
        this.domains = data;
      })
    }
  }

  makeStudyProgramVisible(domain: number): void {
    let element = document.getElementById("studyProgramId");
    if (element !== null) {
      this.filterTable();
      element.classList.remove("hidden-element");
      this.studyService.getAllFilteredStudyPrograms(this.secretary.id, domain).subscribe(data => {
        this.studyPrograms = data;
      })
    }
  }

  makeStudyYearVisible(studyProgram: number): void {
    let element = document.getElementById("studyYearId");
    if (element !== null) {
      this.filterTable();
      element.classList.remove("hidden-element");
      this.studyService.getAllFilteredStudyYears(this.secretary.id, studyProgram).subscribe(data => {
        this.studyYears = data;
      })
    }
  }

  makeStudyGroupVisible(studyYear: number): void {
    let element = document.getElementById("studyGroupId");
    if (element !== null) {
      this.filterTable();
      element.classList.remove("hidden-element");
      this.studyService.getAllFilteredStudyGroups(studyYear).subscribe(data => {
        this.studyGroups = data;
      })
    }
  }

  openPdf(filePath: string): void {
    this.secretaryService.downloadDocument(filePath).subscribe(data => {
      let file = new Blob([data], {type: 'application/pdf'});
      const fileURL = URL.createObjectURL(file);
      window.open(fileURL, '_blank');
    });
  }

  filterTable(): StudentDocumentFilterModel {
    let filter: StudentDocumentFilterModel = {};
    // @ts-ignore
    filter.learningTypeId = this.selectLearningType;
    filter.universityStudiesId = this.selectUniversityStudy;
    filter.domainId = this.selectDomain;
    filter.studyProgramId = this.selectStudyProgram;
    filter.studyYearId = this.selectStudyYear;
    filter.studyGroupId = this.selectStudyGroup;
    filter.status = this.selectStatus;
    filter.secretaryId = this.secretary.id;
    filter.firstName = this.firstName;
    filter.lastName = this.lastName;
    filter.name = this.fileName;
    filter.pageNumber = this.paginator.pageIndex;
    filter.pageSize = this.paginator.pageSize;
    filter.columnName = "name";
    filter.sortDirection = 'asc';
    filter.sortDirection = this.sort.direction;
    this.secretaryService.getStudentDocumentRowModel(filter).subscribe(data => {
      this.dataSource = new MatTableDataSource(data.content);
      this.dataSource.paginator = this.paginator;
      this.dataLength = data.size;
    })
    return filter;
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
}
