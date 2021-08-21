import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {StudentModel} from "../../model/student.model";

@Component({
  selector: 'app-student-details-dialog',
  templateUrl: './student-details-dialog.component.html',
  styleUrls: ['./student-details-dialog.component.scss']
})
export class StudentDetailsDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<StudentDetailsDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: StudentModel) { }

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
