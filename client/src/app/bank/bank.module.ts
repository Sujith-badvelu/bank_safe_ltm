import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";

import { BankRoutingModule } from "./bank-routing.module";
import { ReactiveFormsModule } from "@angular/forms";
import { HttpClientModule } from "@angular/common/http";
import { DashboardComponent } from "./components/dashboard/dashboard.component";
import { CustomersComponent } from "./components/customer/customer.component";
import { NavBarComponent } from "../navbar/navbar.component";
import { AuthModule } from "../auth/auth.module";
import { AccountComponent } from "./components/account/account.component";
import { TransactionComponent } from "./components/transaction/transaction.component";
import { EditAccountComponent } from "./components/accountedit/accountedit.component";
import { EditCustomerComponent } from "./components/customeredit/customeredit.component";

@NgModule({
  declarations: [
    DashboardComponent,
    CustomersComponent,
    AccountComponent,
    TransactionComponent,
    NavBarComponent,
    EditAccountComponent,
    EditCustomerComponent
  ],
  imports: [
    CommonModule,
    BankRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    AuthModule
  ],
  exports: [
    
  ]
})
export class BankModule {}
