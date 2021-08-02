import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {UserLoginComponent} from "./user-login/user-login.component";
import {HomeComponent} from "./home/home.component";
import {ChangePasswordComponent} from "./change-password/change-password.component";
import {SecretaryListComponent} from "./secretary-list/secretary-list.component";

const routes: Routes = [
  {path: 'login', component: UserLoginComponent},
  {path: 'home', component: HomeComponent},
  {path: 'change-password', component:ChangePasswordComponent},
  {path: 'secretary-management', component: SecretaryListComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
