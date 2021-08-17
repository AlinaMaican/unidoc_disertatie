import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {SecretaryService} from "../_services/secretary.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-upload-secretary-document-dialog',
  templateUrl: './upload-secretary-document-dialog.component.html',
  styleUrls: ['./upload-secretary-document-dialog.component.scss']
})
export class UploadSecretaryDocumentDialogComponent{
  uploadFileForm: FormGroup;
  currentDate = new Date();

  constructor(public dialogRef: MatDialogRef<UploadSecretaryDocumentDialogComponent>,
              private secretaryService: SecretaryService,
              private formBuilder: FormBuilder,
              @Inject(MAT_DIALOG_DATA) public data: number) {

    this.uploadFileForm = this.formBuilder.group({
      name: [null, [Validators.required]],
      description: [null],
      endDateOfUpload: [null, Validators.required],
      file: [null, Validators.required]
    });
  }

  submit() {
    if (!this.uploadFileForm.valid) {
      return;
    }
    this.secretaryService.uploadDocument(this.uploadFileForm, this.data).subscribe((message) => {
      location.reload();
    });
  }
  onNoClick(): void {
    this.dialogRef.close();
  }
}
