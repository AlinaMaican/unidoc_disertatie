import {Component, OnInit} from '@angular/core';
import {SecretaryDocumentModel} from "../../model/secretary-document.model";
import {SecretaryService} from "../../_services/secretary.service";
import {MatTableDataSource} from "@angular/material/table";
import {StudentDocumentModel} from "../../model/student-document.model";
import {StudentService} from "../../_services/student.service";
import {UploadOwnDocumentDialogComponent} from "../upload-own-document-dialog/upload-own-document-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {DeleteOwnDocumentDialogComponent} from "../delete-own-document-dialog/delete-own-document-dialog.component";

@Component({
  selector: 'app-created-documents',
  templateUrl: './created-documents.component.html',
  styleUrls: ['./created-documents.component.scss']
})
export class CreatedDocumentsComponent implements OnInit {
  displayedColumns: string[] = ['documentName', 'description', 'dateOfUpload', 'status', 'viewDocument', 'downloadResponseDocument',
    'deleteDocument'];
  clickedRows = new Set<SecretaryDocumentModel>();
  student: any;
  dataSource = new MatTableDataSource<StudentDocumentModel>();

  dataLength:any;
  pageIndex:number = 0;
  pageSize:number = 10;
  pageSizeOptions:number[] = [1, 5, 10];
  constructor(private secretaryService: SecretaryService,
              private studentService: StudentService,
              public dialog: MatDialog) { }

  ngOnInit(): void {
    if (window.sessionStorage.getItem("student") !== null) {
      this.student = JSON.parse(<string>window.sessionStorage.getItem("student"));
      this.loadData();
    }
  }

  loadData(): void{
    this.studentService.getOwnDocuments(this.student.id, this.pageSize, this.pageIndex).subscribe(data => {
      this.dataSource = new MatTableDataSource(data.content);
      this.setPagination(data.totalPages, data.pageable.pageNumber, data.pageable.pageSize)
    })
  }

  setPagination(length: number, startIndex: number, pageSize: number) {
    this.dataLength = length;
    this.pageIndex = startIndex;
    this.pageSize = pageSize;
  }

  onPaginationChange(event: any) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadData();
  }

  openPdf(filePath: string): void{
    this.secretaryService.downloadDocument(filePath).subscribe(data => {
      let file = new Blob([data], {type: 'application/pdf'});
      const fileURL = URL.createObjectURL(file);
      window.open(fileURL, '_blank');
    });
  }

  openUploadDialog() {
    const dialogRef = this.dialog.open(UploadOwnDocumentDialogComponent, {
      width: '500px',
      data: {
        'studentId': this.student.id
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  deleteDocument(documentId: number){
    const dialogRef = this.dialog.open(DeleteOwnDocumentDialogComponent, {
      width: '500px',
      data: {
        'documentId': documentId
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

}
