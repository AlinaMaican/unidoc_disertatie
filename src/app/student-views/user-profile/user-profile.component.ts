import {Component, OnInit} from '@angular/core';
import {UserService} from "../../_services/user.service";
import {UserProfileModel} from "../../model/user-profile.model";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {
  student: UserProfileModel = {};

  constructor(private userService:UserService) {

  }

  ngOnInit(): void {
    if (window.sessionStorage.getItem("student") !== null) {
      let student = JSON.parse(<string>window.sessionStorage.getItem("student"));
      this.userService.getUserProfile(student.user.id).subscribe(data => {
        this.student = data;
      })
    }
  }

}