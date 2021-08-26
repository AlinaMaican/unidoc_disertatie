import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {SecretaryService} from "../../_services/secretary.service";
import {SecretaryDocumentModel} from "../../model/secretary-document.model";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-edit-secretary-document-dialog',
  templateUrl: './edit-secretary-document-dialog.component.html',
  styleUrls: ['./edit-secretary-document-dialog.component.scss']
})
export class EditSecretaryDocumentDialogComponent implements OnInit {
  editFileForm: FormGroup;
  currentDate = new Date();

  constructor(public dialogRef: MatDialogRef<EditSecretaryDocumentDialogComponent>,
              private secretaryService: SecretaryService,
              private formBuilder: FormBuilder,
              @Inject(MAT_DIALOG_DATA) public data: SecretaryDocumentModel,
              private _snackBar: MatSnackBar) {
    this.editFileForm = this.formBuilder.group({
      id: [null, Validators.required],
      name: [null, [Validators.required]],
      description: [null],
      endDateOfUpload: [null, Validators.required]
    });
  }

  ngOnInit(): void {

  }

  submit() {
    if (!this.editFileForm.valid) {
      return;
    }
    this.secretaryService.editSecretaryDocument(this.editFileForm).subscribe((response) => {
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
