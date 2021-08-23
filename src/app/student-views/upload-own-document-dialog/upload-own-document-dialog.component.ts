import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {StudentService} from "../../_services/student.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-upload-own-document-dialog',
  templateUrl: './upload-own-document-dialog.component.html',
  styleUrls: ['./upload-own-document-dialog.component.scss']
})
export class UploadOwnDocumentDialogComponent implements OnInit {
  uploadFileForm: FormGroup;
  constructor(public dialogRef: MatDialogRef<UploadOwnDocumentDialogComponent>,
              private studentService: StudentService,
              private formBuilder: FormBuilder,
              @Inject(MAT_DIALOG_DATA) public data: number,
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
    this.studentService.uploadOwnDocument(this.uploadFileForm, this.data).subscribe((response) => {
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
