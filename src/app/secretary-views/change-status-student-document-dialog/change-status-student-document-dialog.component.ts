import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {StudentDocumentRowModel} from "../../model/student-document-row.model";
import {SecretaryService} from "../../_services/secretary.service";

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
              private secretaryService: SecretaryService) {
    if(this.data.status === 'IN PROGRESS') {
      this.newStatus = 'IN_PROGRESS';
    } else {
      this.newStatus = this.data.status;
    }
  }

  ngOnInit(): void {

  }

  saveDocumentStatus(): void {
    console.log(this.data.documentId);
    console.log(this.comment);
    console.log(this.newStatus);
    this.secretaryService.editStudentDocumentStatus(this.data.documentId, this.newStatus, this.comment).subscribe(() =>{
      this.dialogRef.close();
      location.reload();
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }


}
