import {Component, OnInit} from '@angular/core';
import {SecretaryListModel} from "../../model/secretary-list.model";
import {SecretaryService} from "../../_services/secretary.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {SecretaryAllocationsDialogComponent} from "../secretary-allocations-dialog/secretary-allocations-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {AddSecretaryDialogComponent} from "../add-secretary-dialog/add-secretary-dialog.component";
import {DeleteSecretaryDialogComponent} from "../delete-secretary-dialog/delete-secretary-dialog.component";
import {EditSecretaryDialogComponent} from "../edit-secretary-dialog/edit-secretary-dialog.component";

@Component({
  selector: 'app-secretary-list',
  templateUrl: './secretary-list.component.html',
  styleUrls: ['./secretary-list.component.scss']
})
export class SecretaryListComponent implements OnInit {
  displayedColumns: string[] = ['firstName', 'lastName', 'emailAddress', 'phoneNumbers', 'allocations', 'edit', 'delete'];
  dataSource : SecretaryListModel[] = [];
  clickedRows = new Set<SecretaryListModel>();
  editSecretaryForm: FormGroup = new FormGroup({});


  constructor(private secretaryService: SecretaryService,
              public dialog: MatDialog){ }

  ngOnInit(): void {
    this.secretaryService.getAllSecretaries().subscribe(data=>{
      this.dataSource = data;
    })
    this.editSecretaryForm = new FormGroup(
      {
        'firstName': new FormControl('', Validators.required),
        'lastName': new FormControl('', Validators.required),
        'phoneNumbers': new FormControl('', Validators.required)
      }
    );
  }

  openAllocations(secretaryId: number){
    const dialogRef = this.dialog.open(SecretaryAllocationsDialogComponent, {
      width: '800px',
      data: {
        'secretaryId': secretaryId
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  addSecretary(): void{
    const dialogRef = this.dialog.open(AddSecretaryDialogComponent, {
      width: '500px'
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  editSecretary(secretary: SecretaryListModel): void{
    const dialogRef = this.dialog.open(EditSecretaryDialogComponent, {
      width: '500px',
      data: secretary
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  deleteSecretary(secretary: SecretaryListModel): void{
    const dialogRef = this.dialog.open(DeleteSecretaryDialogComponent, {
      width: '500px',
      data: secretary.id
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

}

