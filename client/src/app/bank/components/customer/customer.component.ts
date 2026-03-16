import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CustomerTS } from '../../types/tstypes/Customerts';

@Component({
  selector: 'app-customers',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.scss']
})
export class CustomersComponent {

  isFormSubmitted: boolean | undefined;
  customerSuccess$: any;
  customerError$: any;
  customerForm!: FormGroup;
  customer: CustomerTS;

  constructor(private formBuilder: FormBuilder) {
    this.customer = new CustomerTS("", "", "", "", "", "");
  }

  ngOnInit(): void {
    this.customerForm = this.formBuilder.group({
      name: ['', Validators.required],
      email: ['', Validators.required],
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onSubmit(): void {
    this.isFormSubmitted = true;

    const value = this.customerForm.value;

    this.customer = new CustomerTS(
      value.name,
      value.email,
      value.username,
      value.password,
      "",
      ""
    );
  }
}