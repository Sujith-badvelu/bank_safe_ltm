import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";

import { AuthRoutingModule } from "./auth-routing.module";
import { AuthComponent } from "./auth.component";
import { LoginComponent } from "./components/login/login.component";
import { ReactiveFormsModule } from "@angular/forms";
import { HttpClientModule } from "@angular/common/http";
import { UserComponent } from './components/user/user.component';
import { LogoutComponent } from './components/logout/logout.component';
import { RouterModule } from "@angular/router";

@NgModule({
  declarations: [AuthComponent, LoginComponent, UserComponent, LogoutComponent],
  imports: [
    CommonModule,
    AuthRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule
  ],
  exports: [
    LogoutComponent
  ]
})
export class AuthModule { }
