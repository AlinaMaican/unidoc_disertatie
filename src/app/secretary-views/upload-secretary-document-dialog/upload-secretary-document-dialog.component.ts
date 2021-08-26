import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {SecretaryService} from "../../_services/secretary.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MatSnackBar} from "@angular/material/snack-bar";

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
              @Inject(MAT_DIALOG_DATA) public data: number,
              private _snackBar: MatSnackBar) {

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
    this.secretaryService.uploadDocument(this.uploadFileForm, this.data).subscribe((response) => {
      if(response.type === "ERROR"){
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
}
