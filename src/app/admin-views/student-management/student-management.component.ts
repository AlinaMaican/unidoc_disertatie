import {Component, OnInit} from '@angular/core';
import {StudentModel} from "../../model/student.model";
import {StudentService} from "../../_services/student.service";
import {MatTableDataSource} from "@angular/material/table";
import {AddSecretaryDialogComponent} from "../add-secretary-dialog/add-secretary-dialog.component";
import {UploadStudentsDialogComponent} from "../upload-students-dialog/upload-students-dialog.component";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-student-management',
  templateUrl: './student-management.component.html',
  styleUrls: ['./student-management.component.scss']
})
export class StudentManagementComponent implements OnInit {
  displayedColumns: string[] = ['firstName', 'lastName', 'emailAddress', 'phoneNumbers', 'study'];
  dataSource = new MatTableDataSource<StudentModel>();
  clickedRows = new Set<StudentModel>();

  dataLength:any;
  pageIndex:number = 0;
  pageSize:number = 10;
  pageSizeOptions:number[] = [1, 5, 10];

  constructor(private studentService: StudentService,
              public dialog: MatDialog) { }

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void{
    this.studentService.getAllStudents(this.pageSize, this.pageIndex).subscribe(data => {
      this.dataSource = new MatTableDataSource(data.content);
      this.setPagination(data.totalPages, data.pageable.pageNumber, data.pageable.pageSize)
    })
  }

  setPagination(length: number, startIndex: number, pageSize: number) {
    this.dataLength = length;
    this.pageIndex = startIndex;
    this.pageSize = pageSize;
  }

  onPaginationChange(event: any) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadData();
  }


  uploadStudents():void{
    const dialogRef = this.dialog.open(UploadStudentsDialogComponent, {
      width: '500px'
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

}
