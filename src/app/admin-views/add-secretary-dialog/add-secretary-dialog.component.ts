import {Component, OnInit} from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {SecretaryService} from "../../_services/secretary.service";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {MatSnackBar} from "@angular/material/snack-bar";
import {RegularExpressionUtil} from "../../util/regular-expression.util";

@Component({
  selector: 'app-add-secretary-dialog',
  templateUrl: './add-secretary-dialog.component.html',
  styleUrls: ['./add-secretary-dialog.component.scss']
})
export class AddSecretaryDialogComponent implements OnInit {
  addSecretaryForm: FormGroup;
  constructor(public dialogRef: MatDialogRef<AddSecretaryDialogComponent>,
              private secretaryService: SecretaryService,
              private formBuilder: FormBuilder,
              private _snackBar: MatSnackBar) {
    this.addSecretaryForm = this.formBuilder.group({
      firstName: [null, [Validators.required]],
      lastName: [null],
      emailAddress: new FormControl(null, [Validators.required, Validators.pattern(RegularExpressionUtil.EMAIL)]),
      phoneNumbers: [null, Validators.required]
    });
  }

  ngOnInit(): void {
  }

  openSnackBar(message: string, action: string, aClass: string) {
    this._snackBar.open(message, action, {panelClass: [aClass]});
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  submit(){
    if (!this.addSecretaryForm.valid) {
      return;
    }
    this.secretaryService.createUserSecretary(this.addSecretaryForm).subscribe((response) => {
      if(response.type === "ERROR"){
        this.openSnackBar(response.message, "Close", "errorSnackBar")
      } else {
        location.reload();
        this.openSnackBar(response.message, "Close", "successSnackBar");
      }
    });
  }
}
