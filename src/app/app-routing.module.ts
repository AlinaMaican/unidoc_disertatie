import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {UserLoginComponent} from "./user-login/user-login.component";
import {HomeComponent} from "./home/home.component";
import {ChangePasswordComponent} from "./change-password/change-password.component";
import {SecretaryListComponent} from "./admin-views/secretary-list/secretary-list.component";
import {DocumentManagementComponent} from "./secretary-views/document-management/document-management.component";
import {StudentDocumentsComponent} from "./secretary-views/student-documents/student-documents.component";
import {RequiredDocumentsComponent} from "./student-views/required-documents/required-documents.component";
import {CreatedDocumentsComponent} from "./student-views/created-documents/created-documents.component";
import {StudentNotificationsComponent} from "./student-views/student-notifications/student-notifications.component";
import {SecretaryNotificationsComponent} from "./secretary-views/secretary-notifications/secretary-notifications.component";

const routes: Routes = [
  {path: '', redirectTo:'login', pathMatch: 'full'},
  {path: 'login', component: UserLoginComponent},
  {path: 'home', component: HomeComponent},
  {path: 'change-password', component:ChangePasswordComponent},
  {path: 'secretary-management', component: SecretaryListComponent},
  {path: 'document-management', component: DocumentManagementComponent},
  {path: 'student-documents', component: StudentDocumentsComponent},
  {path: 'required-documents', component: RequiredDocumentsComponent},
  {path: 'created-documents', component: CreatedDocumentsComponent},
  {path: 'student-notifications', component: StudentNotificationsComponent},
  {path: 'secretary-notifications', component: SecretaryNotificationsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }