import { Component } from '@angular/core';
import { CustomerTS } from '../../types/tstypes/Customerts'; // <-- correct relative path

@Component({
  selector: 'app-customerarray',
  templateUrl: './customerarray.component.html'
  // NOTE: Temporarily remove styleUrls to avoid missing-file compile errors in tests.
  // If you have './customerarray.component.scss' or '.css', you can add it back.
})
export class CustomerarrayComponent {

  customers: CustomerTS[] = [];

  constructor() {
    // Two sample customers as per Day 19 requirement
    const customer1 = new CustomerTS(
      'Srujan',
      'srujan@gmail.com',
      'srujan123',
      'Password123',
      'USER',
      '1'
    );

    const customer2 = new CustomerTS(
      'Iswarya',
      'iswarya@gmail.com',
      'iswarya01',
      'Password456',
      'ADMIN',
      '2'
    );

    this.customers = [customer1, customer2];
  }
}