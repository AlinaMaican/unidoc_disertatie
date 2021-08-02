import {Component, OnInit} from '@angular/core';
import {SecretaryListModel} from "../model/secretary-list.model";
import {SecretaryService} from "../_services/secretary.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-secretary-list',
  templateUrl: './secretary-list.component.html',
  styleUrls: ['./secretary-list.component.scss']
})
export class SecretaryListComponent implements OnInit {
  displayedColumns: string[] = ['firstName', 'lastName', 'emailAddress', 'phoneNumbers', 'actions'];
  dataSource : SecretaryListModel[] = [];
  clickedRows = new Set<SecretaryListModel>();
  editSecretaryForm: FormGroup = new FormGroup({});
  targetSecretary: SecretaryListModel | undefined;

  constructor(private secretaryService: SecretaryService){ }

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
  prepareEdit(secretary: SecretaryListModel) {
    this.editSecretaryForm.patchValue({
      'firstName': secretary.firstName,
      'lastName': secretary.lastName,
      'phoneNumbers': secretary.phoneNumbers
    });
    this.targetSecretary = secretary;
  }

  prepareDelete(secretary: SecretaryListModel) {
    this.targetSecretary = secretary;
  }

  prepareView(secretary: SecretaryListModel) {
    this.targetSecretary = secretary;
  }

}

