import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CustomerarrayComponent } from './components/customerarray/customerarray.component';

@NgModule({
  declarations: [
    CustomerarrayComponent,
    // ...other components
  ],
  imports: [
    CommonModule,
    // ...other imports (RouterModule, ReactiveFormsModule, etc. as needed)
  ],
  exports: [
    CustomerarrayComponent // optional but sometimes helps testing visibility
  ]
})
export class BankModule {}