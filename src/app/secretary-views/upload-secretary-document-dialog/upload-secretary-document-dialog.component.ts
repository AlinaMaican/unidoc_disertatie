import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {SecretaryService} from "../../_services/secretary.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MatSnackBar} from "@angular/material/snack-bar";
import {StudyModel} from "../../model/study.model";
import {StudyService} from "../../_services/study.service";

@Component({
  selector: 'app-upload-secretary-document-dialog',
  templateUrl: './upload-secretary-document-dialog.component.html',
  styleUrls: ['./upload-secretary-document-dialog.component.scss']
})
export class UploadSecretaryDocumentDialogComponent implements OnInit {
  uploadFileForm: FormGroup;
  currentDate = new Date();
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

  constructor(public dialogRef: MatDialogRef<UploadSecretaryDocumentDialogComponent>,
              private secretaryService: SecretaryService,
              private studyService: StudyService,
              private formBuilder: FormBuilder,
              private _snackBar: MatSnackBar) {

    this.uploadFileForm = this.formBuilder.group({
      name: [null, [Validators.required]],
      description: [null],
      endDateOfUpload: [null, Validators.required],
      file: [null, Validators.required],
      learningType: [null, Validators.required],
      universityStudy: [null, Validators.required],
      domain: [null, Validators.required],
      studyProgram: [null, Validators.required],
      studyYear: [null, Validators.required]
    });
  }

  ngOnInit() {
    if (window.sessionStorage.getItem("secretary") !== null) {
      this.secretary = JSON.parse(<string>window.sessionStorage.getItem("secretary"));
      this.studyService.getAllowedLearningTypes(this.secretary.id).subscribe(data => {
        this.learningTypes = data;
      })
    }
  }

  submit() {
    if (!this.uploadFileForm.valid) {
      return;
    }
    this.secretaryService.uploadDocument(this.uploadFileForm, this.secretary.id).subscribe((response) => {
      if (response.type === "ERROR") {
        this.openSnackBar(response.message, "Close", "errorSnackBar")
      } else {
        location.reload();
        this.openSnackBar(response.message, "Close", "successSnackBar");
      }
    });
  }

  openSnackBar(message: string, action: string, aClass: string) {
    this._snackBar.open(message, action, {panelClass: [aClass]});
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  makeUniversityStudyVisible(learningType: string): void {
    let element = document.getElementById("universityStudyId");
    if (element !== null) {
      element.classList.remove("hidden-element");
      this.studyService.getAllowedUniversityStudies(this.secretary.id, learningType).subscribe(data => {
        this.universityStudies = data;
      })
    }
  }

  makeDomainVisible(universityStudy: string): void {
    let element = document.getElementById("domainId");
    if (element !== null) {
      element.classList.remove("hidden-element");
      this.studyService.getAllowedDomains(this.secretary.id, this.selectLearningType, universityStudy).subscribe(data => {
        let domain: StudyModel = {};
        domain.value = "ALL"
        domain.name = "ALL";
        this.domains = data;
        this.domains.unshift(domain)
      })
    }
  }

  makeStudyProgramVisible(domain: string): void {
    let element = document.getElementById("studyProgramId");
    if (element !== null) {
      element.classList.remove("hidden-element");
      this.studyService.getAllowedStudyPrograms(this.secretary.id, this.selectLearningType, this.selectUniversityStudy,
        domain).subscribe(data => {
        let studyProgram: StudyModel = {};
        studyProgram.value = "ALL"
        studyProgram.name = "ALL";
        this.studyPrograms = data;
        this.studyPrograms.unshift(studyProgram);
      })
    }
  }

  makeStudyYearVisible(studyProgram: string): void {
    let element = document.getElementById("studyYearId");
    if (element !== null) {
      element.classList.remove("hidden-element");
      this.studyService.getAllowedStudyYears(this.secretary.id, this.selectLearningType, this.selectUniversityStudy,
        this.selectDomain, studyProgram).subscribe(data => {
        let studyYear: StudyModel = {};
        studyYear.value = "ALL"
        studyYear.name = "ALL";
        this.studyYears = data;
        this.studyYears.unshift(studyYear);
      })
    }
  }
}
