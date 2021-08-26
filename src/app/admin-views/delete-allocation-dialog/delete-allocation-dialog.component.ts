import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {MatSnackBar} from "@angular/material/snack-bar";
import {StudyService} from "../../_services/study.service";

@Component({
  selector: 'app-delete-allocation-dialog',
  templateUrl: './delete-allocation-dialog.component.html',
  styleUrls: ['./delete-allocation-dialog.component.scss']
})
export class DeleteAllocationDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<DeleteAllocationDialogComponent>,
              private studyService: StudyService,
              @Inject(MAT_DIALOG_DATA) public data: number,
              private _snackBar: MatSnackBar) { }

  ngOnInit(): void {
  }
  submit() {
    this.studyService.deleteAllocation(this.data).subscribe((response) => {
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
