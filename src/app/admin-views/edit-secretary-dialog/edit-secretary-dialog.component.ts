import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {SecretaryService} from "../../_services/secretary.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {SecretaryListModel} from "../../model/secretary-list.model";

@Component({
  selector: 'app-edit-secretary-dialog',
  templateUrl: './edit-secretary-dialog.component.html',
  styleUrls: ['./edit-secretary-dialog.component.scss']
})
export class EditSecretaryDialogComponent implements OnInit {
  editSecretaryForm: FormGroup;

  constructor(public dialogRef: MatDialogRef<EditSecretaryDialogComponent>,
              private secretaryService: SecretaryService,
              @Inject(MAT_DIALOG_DATA) public data: SecretaryListModel,
              private _snackBar: MatSnackBar,
              private formBuilder: FormBuilder) {
    this.editSecretaryForm = this.formBuilder.group({
      id: [null],
      firstName: [null, [Validators.required]],
      lastName: [null, Validators.required],
      phoneNumbers: [null]
    });
  }

  ngOnInit(): void {
  }

  submit() {
    if (!this.editSecretaryForm.valid) {
      return;
    }
    this.secretaryService.editSecretary(this.editSecretaryForm).subscribe((response) => {
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
