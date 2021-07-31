import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {UserLoginComponent} from "./user-login/user-login.component";
import {HomeComponent} from "./home/home.component";
import {ChangePasswordComponent} from "./change-password/change-password.component";

const routes: Routes = [
  {path: 'login', component: UserLoginComponent},
  {path: 'home', component: HomeComponent},
  {path: 'change-password', component:ChangePasswordComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
