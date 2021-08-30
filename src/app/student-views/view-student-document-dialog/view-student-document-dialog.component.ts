import {Component, Inject, OnInit} from '@angular/core';
import {SecretaryService} from "../../_services/secretary.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {StudentDocumentModel} from "../../model/student-document.model";
import {StudentService} from "../../_services/student.service";


@Component({
  selector: 'app-view-student-document-dialog',
  templateUrl: './view-student-document-dialog.component.html',
  styleUrls: ['./view-student-document-dialog.component.scss']
})
export class ViewStudentDocumentDialogComponent implements OnInit {
  student: any;
  constructor(private studentService: StudentService,
              public dialogRef: MatDialogRef<ViewStudentDocumentDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: StudentDocumentModel) { }

  ngOnInit(): void {
    if (window.sessionStorage.getItem("student") !== null) {
      this.student = JSON.parse(<string>window.sessionStorage.getItem("student"));
    }
  }

  openPdf(filePath: string): void {
    this.studentService.downloadEncryptedDocument(filePath, this.student.id).subscribe(data => {
      let file = new Blob([data], {type: 'application/pdf'});
      const fileURL = URL.createObjectURL(file);
      window.open(fileURL, '_blank');
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
