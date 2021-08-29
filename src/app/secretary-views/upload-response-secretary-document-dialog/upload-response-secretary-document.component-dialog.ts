import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {MatSnackBar} from "@angular/material/snack-bar";
import {SecretaryService} from "../../_services/secretary.service";

@Component({
  selector: 'app-upload-response-secretary-document',
  templateUrl: './upload-response-secretary-document.component-dialog.html',
  styleUrls: ['./upload-response-secretary-document.component-dialog.scss']
})
export class UploadResponseSecretaryDocumentComponentDialog implements OnInit {
  uploadFileForm: FormGroup;
  constructor(public dialogRef: MatDialogRef<UploadResponseSecretaryDocumentComponentDialog>,
              private secretaryService: SecretaryService,
              private formBuilder: FormBuilder,
              @Inject(MAT_DIALOG_DATA) public data:any,
              private _snackBar: MatSnackBar) {
    this.uploadFileForm = this.formBuilder.group({
      name: [null, Validators.required],
      description: [null],
      file: [null, Validators.required]
    });
  }

  ngOnInit(): void {
  }

  submit() {
    if (!this.uploadFileForm.valid) {
      return;
    }
    this.secretaryService.uploadResponseDocument(this.uploadFileForm, this.data.secretaryAllocationId,
      this.data.studentDocumentId).subscribe((response) => {
      if(response.type === "ERROR"){
        this.openSnackBar(response.message, "Close", "errorSnackBar")
      } else {
        location.reload();
        this.openSnackBar(response.message, "Close", "successSnackBar");
      }
    });
  }
  onNoClick(): void {
    this.dialogRef.close();
  }

  openSnackBar(message: string, action: string, aClass: string) {
    this._snackBar.open(message, action, {panelClass: [aClass]});
  }

}
