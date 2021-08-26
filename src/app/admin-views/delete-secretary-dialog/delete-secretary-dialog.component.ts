import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {MatSnackBar} from "@angular/material/snack-bar";
import {SecretaryService} from "../../_services/secretary.service";

@Component({
  selector: 'app-delete-secretary-dialog',
  templateUrl: './delete-secretary-dialog.component.html',
  styleUrls: ['./delete-secretary-dialog.component.scss']
})
export class DeleteSecretaryDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<DeleteSecretaryDialogComponent>,
              private secretaryService: SecretaryService,
              @Inject(MAT_DIALOG_DATA) public data: number,
              private _snackBar: MatSnackBar) { }

  ngOnInit(): void {
  }

  submit() {
    this.secretaryService.deleteSecretary(this.data).subscribe((response) => {
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
