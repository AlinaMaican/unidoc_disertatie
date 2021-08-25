import {Component, OnInit} from '@angular/core';
import {SecretaryListModel} from "../../model/secretary-list.model";
import {SecretaryService} from "../../_services/secretary.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {StudyDetailsModel} from "../../model/study-details.model";
import {SecretaryAllocationsDialogComponent} from "../secretary-allocations-dialog/secretary-allocations-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {AddSecretaryDialogComponent} from "../add-secretary-dialog/add-secretary-dialog.component";

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

  openAllocations(allocations: StudyDetailsModel[], secretaryId: number){
    const dialogRef = this.dialog.open(SecretaryAllocationsDialogComponent, {
      width: '800px',
      data: {
        'allocations' :allocations,
        'secretaryId': secretaryId
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  addSecretary(): void{
    const dialogRef = this.dialog.open(AddSecretaryDialogComponent, {
      width: '500px',
      height: '1000px'
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  editSecretary(secretary: SecretaryListModel): void{

  }

  deleteSecretary(secretary: SecretaryListModel): void{

  }

}

