import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {SecretaryService} from "../../_services/secretary.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {StudentService} from "../../_services/student.service";

@Component({
  selector: 'app-upload-students-dialog',
  templateUrl: './upload-students-dialog.component.html',
  styleUrls: ['./upload-students-dialog.component.scss']
})
export class UploadStudentsDialogComponent implements OnInit {

  uploadFileForm: FormGroup;
  constructor(public dialogRef: MatDialogRef<UploadStudentsDialogComponent>,
              private studentService: StudentService,
              private formBuilder: FormBuilder,
              @Inject(MAT_DIALOG_DATA) public data:any,
              private _snackBar: MatSnackBar) {
    this.uploadFileForm = this.formBuilder.group({
      file: [null, Validators.required]
    });
  }

  ngOnInit(): void {
  }

  submit() {
    if (!this.uploadFileForm.valid) {
      return;
    }
    this.studentService.uploadStudents(this.uploadFileForm).subscribe((response) => {
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
