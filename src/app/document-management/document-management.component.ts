import {Component, OnInit} from '@angular/core';
import {SecretaryService} from "../_services/secretary.service";
import {SecretaryAllocationModel} from "../model/secretary-allocation.model";
import {SecretaryDocumentModel} from "../model/secretary-document.model";
import {MatDialog} from "@angular/material/dialog";
import {UploadSecretaryDocumentDialogComponent} from "../upload-secretary-document-dialog/upload-secretary-document-dialog.component";
import {SecretaryDocumentTableDataModel} from "../model/secretary-document-table-data.model";
import {EditSecretaryDocumentDialogComponent} from "../edit-secretary-document-dialog/edit-secretary-document-dialog.component";

@Component({
  selector: 'app-document-management',
  templateUrl: './document-management.component.html',
  styleUrls: ['./document-management.component.scss']
})
export class DocumentManagementComponent implements OnInit {
  displayedColumns: string[] = ['documentName', 'description', 'endDateOfUpload', 'viewDocument', 'edit'];
  clickedRows = new Set<SecretaryDocumentModel>();
  secretaryAllocations : SecretaryDocumentTableDataModel[] = [];
  secretary: any;

  constructor(private secretaryService: SecretaryService, public dialog: MatDialog) {}

  ngOnInit(): void {
    if (window.sessionStorage.getItem("secretary") !== null){
      this.secretary = JSON.parse(<string>window.sessionStorage.getItem("secretary"));
    }
    this.secretaryService.getAllAllocationsBySecretaryId(this.secretary.id).subscribe(data =>{
     data.forEach((allocation: SecretaryAllocationModel) => {
       this.secretaryService.getSecretaryDocumentsByAllocationId(allocation.allocationId).subscribe(data => {
         let secretaryAllocation = <SecretaryDocumentTableDataModel>{
           allocationId: allocation.allocationId,
           allocationName : allocation.learningType + ", " + allocation.universityStudyType + ", "
           + allocation.domain + ", " + allocation.studyProgram + ", " + allocation.studyYear,
           tableData : data
         };
         this.secretaryAllocations.push(secretaryAllocation);
         });
       });
     });
  }

  prepareEdit(document: SecretaryDocumentModel) {
    const dialogRef = this.dialog.open(EditSecretaryDocumentDialogComponent, {
      width: '500px',
      data: document
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  openUploadDialog(allocationId: number){
    const dialogRef = this.dialog.open(UploadSecretaryDocumentDialogComponent, {
      width: '500px',
      data: allocationId
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  openPdf(filePath: string): void{
    this.secretaryService.downloadDocument(filePath).subscribe(data => {
      let file = new Blob([data], {type: 'application/pdf'});
      const fileURL = URL.createObjectURL(file);
      window.open(fileURL, '_blank');
   });
  }

}
