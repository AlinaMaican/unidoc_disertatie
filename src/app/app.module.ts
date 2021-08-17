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
import {ReactiveFormsModule} from "@angular/forms";
import { HomeComponent } from './home/home.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { NavbarComponent } from './navbar/navbar.component';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import { SecretaryListComponent } from './secretary-list/secretary-list.component';
import {MatTableModule} from "@angular/material/table";
import { DocumentManagementComponent } from './document-management/document-management.component';
import { UploadSecretaryDocumentDialogComponent } from './upload-secretary-document-dialog/upload-secretary-document-dialog.component';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {NgxMatFileInputModule} from "@angular-material-components/file-input";
import {MatDialogModule} from "@angular/material/dialog";
import {MatButtonModule} from "@angular/material/button";
import {MatInputModule} from "@angular/material/input";
import {MatNativeDateModule} from "@angular/material/core";
import { EditSecretaryDocumentDialogComponent } from './edit-secretary-document-dialog/edit-secretary-document-dialog.component';

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
    EditSecretaryDocumentDialogComponent
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
        MatNativeDateModule
    ],
  providers: [
    authInterceptorProviders,
    {
      provide: APP_INITIALIZER,
      useFactory: translationServiceFactory,
      deps: [TranslateService],
      multi: true,
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
