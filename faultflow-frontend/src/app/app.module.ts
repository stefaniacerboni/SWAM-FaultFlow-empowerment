import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';


import { AppComponent } from './app.component';
import { LoginComponent } from './features/login/login.component';
import { AppRoutingModule } from './app-routing.module';
import { SystemsComponent} from './features/systems/systems.component';
import { ImportComponent } from './features/import/import.component';
import { EditProfileComponent } from './features/edit-profile/edit-profile.component';
import {HomeComponent} from "./features/home/home.component";

import { AuthService } from './core/services/auth.service';
import { SystemsService } from './core/services/systems.service';
import { ImportService } from './core/services/import.service';
import { UserService } from './core/services/user.service';
import { JwtInterceptor } from './core/services/jwt.interceptor';
import { LayoutComponent } from './shared/layout/layout.component';
import {AuthGuard} from "./core/services/auth-guard.service";
import { JsonSidebarComponent } from './features/json-sidebar/json-sidebar.component';
import { DocumentationModalComponent } from './features/documentation-modal/documentation-modal.component';
import { AnalyzeSidebarComponent } from './features/analyze-sidebar/analyze-sidebar.component';



@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    SystemsComponent,
    ImportComponent,
    EditProfileComponent,
    LoginComponent,
    LayoutComponent,
    JsonSidebarComponent,
    DocumentationModalComponent,
    AnalyzeSidebarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [
    AuthService,
    SystemsService,
    ImportService,
    UserService,
    AuthGuard,
    {
    provide: HTTP_INTERCEPTORS,
    useClass: JwtInterceptor,
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
