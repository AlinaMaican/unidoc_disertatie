import {Component, OnInit} from '@angular/core';
import {SecretaryDocumentModel} from "../../model/secretary-document.model";
import {StudentService} from "../../_services/student.service";
import {RequiredDocumentRowModel} from "../../model/required-document-row.model";
import {SecretaryService} from "../../_services/secretary.service";
import {MatDialog} from "@angular/material/dialog";
import {UploadStudentDocumentDialogComponent} from "../upload-student-document-dialog/upload-student-document-dialog.component";
import {StudentDocumentModel} from "../../model/student-document.model";
import {ViewStudentDocumentDialogComponent} from "../view-student-document-dialog/view-student-document-dialog.component";

@Component({
  selector: 'app-required-documents',
  templateUrl: './required-documents.component.html',
  styleUrls: ['./required-documents.component.scss']
})
export class RequiredDocumentsComponent implements OnInit {
  displayedColumns: string[] = ['secretaryDocumentName', 'endDateOfUpload', 'downloadSecretaryDocument', 'uploadStudentDocument', 'viewUploadedDocument'];
  clickedRows = new Set<SecretaryDocumentModel>();
  dataSource: RequiredDocumentRowModel[] = [];
  student: any;
  downloadTooltipMessage = "Click here to view and download the document uploaded by the secretary";
  uploadTooltipMessage = "Click here to upload the required file";
  viewUploadedDocumentsTooltipMessage = "Click here to view the uploaded document details"

  constructor(private studentService: StudentService,
              private secretaryService: SecretaryService,
              public dialog: MatDialog) {
  }

  ngOnInit(): void {
    if (window.sessionStorage.getItem("student") !== null) {
      this.student = JSON.parse(<string>window.sessionStorage.getItem("student"));
      this.studentService.getRequiredDocuments(this.student.id).subscribe(data => {
        this.dataSource = data;
      })
    }

  }

  openPdf(filePath: string): void {
    this.secretaryService.downloadDocument(filePath).subscribe(data => {
      let file = new Blob([data], {type: 'application/pdf'});
      const fileURL = URL.createObjectURL(file);
      window.open(fileURL, '_blank');
    });
  }

  openUploadDialog(secretaryDocumentId: number) {
    const dialogRef = this.dialog.open(UploadStudentDocumentDialogComponent, {
      width: '500px',
      data: {
        'secretaryDocumentId': secretaryDocumentId,
        'studentId': this.student.id
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  openViewDocumentDataDialog(studentDocumentModel: StudentDocumentModel) {
    const dialogRef = this.dialog.open(ViewStudentDocumentDialogComponent, {
      width: '500px',
      data: studentDocumentModel
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }
}
