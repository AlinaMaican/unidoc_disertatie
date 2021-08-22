import {Component, Inject, OnInit} from '@angular/core';
import {SecretaryService} from "../../_services/secretary.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {StudentDocumentModel} from "../../model/student-document.model";


@Component({
  selector: 'app-view-student-document-dialog',
  templateUrl: './view-student-document-dialog.component.html',
  styleUrls: ['./view-student-document-dialog.component.scss']
})
export class ViewStudentDocumentDialogComponent implements OnInit {

  constructor(private secretaryService: SecretaryService,
              public dialogRef: MatDialogRef<ViewStudentDocumentDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: StudentDocumentModel) { }

  ngOnInit(): void {
  }

  openPdf(filePath: string): void {
    this.secretaryService.downloadDocument(filePath).subscribe(data => {
      let file = new Blob([data], {type: 'application/pdf'});
      const fileURL = URL.createObjectURL(file);
      window.open(fileURL, '_blank');
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
