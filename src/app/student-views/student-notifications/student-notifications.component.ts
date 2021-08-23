import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {StudentNotificationModel} from "../../model/student-notification.model";
import {StudentService} from "../../_services/student.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-student-notifications',
  templateUrl: './student-notifications.component.html',
  styleUrls: ['./student-notifications.component.scss']
})
export class StudentNotificationsComponent implements OnInit {
  displayedColumns: string[] = ['documentName', 'message', 'date', 'viewDocument', 'seen'];
  clickedRows = new Set<StudentNotificationModel>();
  student: any;
  dataSource = new MatTableDataSource<StudentNotificationModel>();

  dataLength:any;
  pageIndex:number = 0;
  pageSize:number = 1;
  pageSizeOptions:number[] = [1, 5, 10];

  columnName = 'date';
  direction = 'desc';

  checkTooltipMessage = "Click here to mark the notification as seen!"

  constructor(private studentService: StudentService,
              private router: Router) { }

  ngOnInit(): void {
    if (window.sessionStorage.getItem("student") !== null) {
      this.student = JSON.parse(<string>window.sessionStorage.getItem("student"));
      this.loadData();
    }
  }

  loadData(): void{
    this.studentService.getStudentNotifications(this.student.id, this.pageSize, this.pageIndex, this.columnName, this.direction).subscribe(data => {
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

  sortData(event: any){
    this.columnName = event['active'];
    this.direction = event['direction'];
    this.loadData();
  }

  goToTheDocument(documentId: number){
    this.router.navigate(["/required-documents"]);
  }

  checkNotification(notificationId: number){
    return this.studentService.checkNotification(notificationId).subscribe(() =>{
      window.location.reload();
    })
  }

  checkSeen(seen: boolean) : boolean{
    return seen;
  }

}
