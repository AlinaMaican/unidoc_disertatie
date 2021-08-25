import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {StudyDetailsModel} from "../../model/study-details.model";
import {StudyModel} from "../../model/study.model";
import {StudyService} from "../../_services/study.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-secretary-allocations-dialog',
  templateUrl: './secretary-allocations-dialog.component.html',
  styleUrls: ['./secretary-allocations-dialog.component.scss']
})
export class SecretaryAllocationsDialogComponent implements OnInit {
  dataSource: StudyDetailsModel[] = [];
  displayedColumns: string[] = ['learningType', 'universityStudiesType', 'domain', 'studyProgram', 'studyYear', 'edit', 'delete'];
  clickedRows = new Set<StudyDetailsModel>();
  learningTypes: StudyModel[] = [];
  universityStudies: StudyModel[] = [];
  domains: StudyModel[] = [];
  studyPrograms: StudyModel[] = [];
  studyYears: StudyModel[] = [];
  selectLearningType: any;
  selectUniversityStudy: any;
  selectDomain: any;
  selectStudyProgram: any;
  selectStudyYear: any;
  secretary: any;

  constructor(public dialogRef: MatDialogRef<SecretaryAllocationsDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private studyService: StudyService,
              private _snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.dataSource = this.data.allocations;
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  editAllocation(allocation: StudyDetailsModel): void {

  }

  deleteAllocation(allocation: StudyDetailsModel): void {

  }

  addAllocation(): void {
    let element = document.getElementById("learningTypeId");
    if (element !== null) {
      element.classList.remove("hidden-element");
      this.studyService.getAllLearningTypes().subscribe(data => {
        this.learningTypes = data;
      })
    }
  }

  makeUniversityStudyVisible(learningType: number): void {
    let element = document.getElementById("universityStudyId");
    if (element !== null) {
      element.classList.remove("hidden-element");
      this.studyService.getAllUniversityStudies(learningType).subscribe(data => {
        this.universityStudies = data;
      })
    }
  }

  makeDomainVisible(universityStudy: number): void {
    let element = document.getElementById("domainId");
    if (element !== null) {
      element.classList.remove("hidden-element");
      this.studyService.getAllDomains(universityStudy).subscribe(data => {
        this.domains = data;
      })
    }
  }

  makeStudyProgramVisible(domain: number): void {
    let element = document.getElementById("studyProgramId");
    if (element !== null) {
      element.classList.remove("hidden-element");
      this.studyService.getAllStudyPrograms(domain).subscribe(data => {
        this.studyPrograms = data;
      })
    }
  }

  makeStudyYearVisible(studyProgram: number): void {
    let element = document.getElementById("studyYearId");
    if (element !== null) {
      element.classList.remove("hidden-element");
      this.studyService.getAllStudyYears(studyProgram).subscribe(data => {
        this.studyYears = data;
      })
    }
  }

  makeSaveButtonVisible(): void {
    let element = document.getElementById("saveAllocationButtonId");
    if (element !== null) {
      element.classList.remove("hidden-element");
    }
  }

  saveAllocation() {
    let allocation = {
      secretaryId: this.data.secretaryId,
      learningTypeId: this.selectLearningType,
      universityStudyTypeId: this.selectUniversityStudy,
      domainId: this.selectDomain,
      studyProgramId: this.selectStudyProgram,
      studyYearId: this.selectStudyYear
    }
    this.studyService.createAllocation(allocation).subscribe(response => {
      if (response.type === "ERROR") {
        this.openSnackBar(response.message, "Close", "errorSnackBar")
      } else {
        let element1 = document.getElementById("studyYearId");
        let element2 = document.getElementById("studyProgramId");
        let element3 = document.getElementById("domainId");
        let element4 = document.getElementById("universityStudyId");
        let element5 = document.getElementById("learningTypeId");
        let element6 = document.getElementById("saveAllocationButtonId");

        if (element1 !== null) {
          element1.classList.add("hidden-element");
        }
        if (element2 !== null) {
          element2.classList.add("hidden-element");
        }
        if (element3 !== null) {
          element3.classList.add("hidden-element");
        }
        if (element4 !== null) {
          element4.classList.add("hidden-element");
        }
        if (element5 !== null) {
          element5.classList.add("hidden-element");
        }
        if (element6 !== null) {
          element6.classList.add("hidden-element");
        }
        this.openSnackBar(response.message, "Close", "successSnackBar");
      }
    })

  }

  openSnackBar(message: string, action: string, aClass: string) {
    this._snackBar.open(message, action, {panelClass: [aClass]});
  }
}
