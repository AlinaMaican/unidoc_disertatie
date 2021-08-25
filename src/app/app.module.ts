import {APP_INITIALIZER, NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { UserLoginComponent } from './user-login/user-login.component';
import {authInterceptorProviders} from "./_helpers/auth.interceptor";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {TranslateLoader, TranslateModule, TranslateService} from "@ngx-translate/core";
import {TranslateHttpLoader} from "@ngx-translate/http-loader";
import {LanguageUtil} from "./util/language.util";
import {take} from "rxjs/operators";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { HomeComponent } from './home/home.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { NavbarComponent } from './navbar/navbar.component';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import { SecretaryListComponent } from './admin-views/secretary-list/secretary-list.component';
import {MatTableModule} from "@angular/material/table";
import { DocumentManagementComponent } from './secretary-views/document-management/document-management.component';
import { UploadSecretaryDocumentDialogComponent } from './secretary-views/upload-secretary-document-dialog/upload-secretary-document-dialog.component';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {NgxMatFileInputModule} from "@angular-material-components/file-input";
import {MatDialogModule} from "@angular/material/dialog";
import {MatButtonModule} from "@angular/material/button";
import {MatInputModule} from "@angular/material/input";
import {MatNativeDateModule} from "@angular/material/core";
import { EditSecretaryDocumentDialogComponent } from './secretary-views/edit-secretary-document-dialog/edit-secretary-document-dialog.component';
import { StudentDocumentsComponent } from './secretary-views/student-documents/student-documents.component';
import {MatSelectModule} from "@angular/material/select";
import {MatSortModule} from "@angular/material/sort";
import {MatPaginatorModule} from "@angular/material/paginator";
import { StudentDetailsDialogComponent } from './secretary-views/student-details-dialog/student-details-dialog.component';
import {MatTooltipModule} from "@angular/material/tooltip";
import { ChangeStatusStudentDocumentDialogComponent } from './secretary-views/change-status-student-document-dialog/change-status-student-document-dialog.component';
import { RequiredDocumentsComponent } from './student-views/required-documents/required-documents.component';
import { UploadStudentDocumentDialogComponent } from './student-views/upload-student-document-dialog/upload-student-document-dialog.component';
import { ViewStudentDocumentDialogComponent } from './student-views/view-student-document-dialog/view-student-document-dialog.component';
import { CreatedDocumentsComponent } from './student-views/created-documents/created-documents.component';
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {MatBadgeModule} from "@angular/material/badge";
import { StudentNotificationsComponent } from './student-views/student-notifications/student-notifications.component';
import { SecretaryNotificationsComponent } from './secretary-views/secretary-notifications/secretary-notifications.component';
import { PaginationComponent } from './pagination/pagination.component';
import { UploadOwnDocumentDialogComponent } from './student-views/upload-own-document-dialog/upload-own-document-dialog.component';
import { UserProfileComponent } from './student-views/user-profile/user-profile.component';
import { RoleGuardComponent } from './admin-views/role-guard/role-guard.component';
import { StudentRoleGuardComponent } from './student-views/student-role-guard/student-role-guard.component';
import { SecretaryRoleGuardComponent } from './secretary-views/secretary-role-guard/secretary-role-guard.component';
import { DeleteOwnDocumentDialogComponent } from './student-views/delete-own-document-dialog/delete-own-document-dialog.component';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}

export function translationServiceFactory(translate: TranslateService): () => Promise<any> {
  // translationService;
  return () => {
    const langLoaded = translate.onLangChange.pipe(
      take(1),
    ).toPromise();

    translate.addLangs(['en', 'ro']);
    translate.setDefaultLang(LanguageUtil.getLanguage());
    translate.use(LanguageUtil.getLanguage());

    return langLoaded;
  };
}

@NgModule({
  declarations: [
    AppComponent,
    UserLoginComponent,
    HomeComponent,
    ChangePasswordComponent,
    NavbarComponent,
    SecretaryListComponent,
    DocumentManagementComponent,
    UploadSecretaryDocumentDialogComponent,
    EditSecretaryDocumentDialogComponent,
    StudentDocumentsComponent,
    StudentDetailsDialogComponent,
    ChangeStatusStudentDocumentDialogComponent,
    RequiredDocumentsComponent,
    UploadStudentDocumentDialogComponent,
    ViewStudentDocumentDialogComponent,
    CreatedDocumentsComponent,
    StudentNotificationsComponent,
    SecretaryNotificationsComponent,
    PaginationComponent,
    UploadOwnDocumentDialogComponent,
    UserProfileComponent,
    DeleteOwnDocumentDialogComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        HttpClientModule,
        TranslateModule.forRoot({
            defaultLanguage: 'en',
            loader: {
                provide: TranslateLoader,
                useFactory: HttpLoaderFactory,
                deps: [HttpClient]
            }
        }),
        ReactiveFormsModule,
        MatToolbarModule,
        MatIconModule,
        MatTableModule,
        MatFormFieldModule,
        MatDatepickerModule,
        NgxMatFileInputModule,
        MatDialogModule,
        MatButtonModule,
        MatInputModule,
        MatNativeDateModule,
        MatSelectModule,
        MatSortModule,
        MatPaginatorModule,
        FormsModule,
        MatTooltipModule,
        MatSnackBarModule,
        MatBadgeModule
    ],
  providers: [
    authInterceptorProviders,
    {
      provide: APP_INITIALIZER,
      useFactory: translationServiceFactory,
      deps: [TranslateService],
      multi: true,
    },
    RoleGuardComponent,
    StudentRoleGuardComponent,
    SecretaryRoleGuardComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
