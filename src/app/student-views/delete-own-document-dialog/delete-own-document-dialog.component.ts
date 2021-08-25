import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {StudentService} from "../../_services/student.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-delete-own-document-dialog',
  templateUrl: './delete-own-document-dialog.component.html',
  styleUrls: ['./delete-own-document-dialog.component.scss']
})
export class DeleteOwnDocumentDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<DeleteOwnDocumentDialogComponent>,
              private studentService: StudentService,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private _snackBar: MatSnackBar) { }

  ngOnInit(): void {
  }

  submit() {
    this.studentService.deleteOwnDocument(this.data.documentId).subscribe((response) => {
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
