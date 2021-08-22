import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {StudentDocumentRowModel} from "../../model/student-document-row.model";
import {SecretaryService} from "../../_services/secretary.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-change-status-student-document-dialog',
  templateUrl: './change-status-student-document-dialog.component.html',
  styleUrls: ['./change-status-student-document-dialog.component.scss']
})
export class ChangeStatusStudentDocumentDialogComponent implements OnInit {
  comment: string = '';
  inProgress = 'IN_PROGRESS';
  valid = 'VALID';
  invalid = 'INVALID';
  newStatus = '';

  constructor(public dialogRef: MatDialogRef<ChangeStatusStudentDocumentDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: StudentDocumentRowModel,
              private secretaryService: SecretaryService,
              private _snackBar: MatSnackBar) {
    if(this.data.status === 'IN PROGRESS') {
      this.newStatus = 'IN_PROGRESS';
    } else {
      this.newStatus = this.data.status;
    }
  }

  ngOnInit(): void {

  }

  saveDocumentStatus(): void {
    this.secretaryService.editStudentDocumentStatus(this.data.documentId, this.newStatus, this.comment).subscribe((response) =>{
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
