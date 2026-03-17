import { NgModule } from "@angular/core";
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from "@angular/router";
import { AppComponent } from './app.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptor } from "./auth.interceptors";

const routes: Routes = [
  {
    path: "auth",
    loadChildren: () => import("./auth/auth.module").then((m) => m.AuthModule),
  },

  {
    path: "bank",
    loadChildren: () => import("./bank/bank.module").then((m) => m.BankModule),
  },
  {
    path: "",
    pathMatch: "full",
    redirectTo: "/auth",
  },
];

@NgModule({
  declarations: [
    AppComponent // Declare AppComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(routes)
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    },
  ],
  bootstrap: [AppComponent] // Bootstrap AppComponent
})
export class AppRoutingModule {}