import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AccountTS } from '../../types/tstypes/Accountts';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html'
})
export class AccountComponent implements OnInit {

  accountForm!: FormGroup;
  account?: AccountTS;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.accountForm = this.fb.group({
      customer_id: ['', [Validators.required]],
      balance: [null, [Validators.required, Validators.min(0)]]
    });
  }

  onSubmit(): void {
    if (this.accountForm.invalid) {
      this.account = undefined;
      return;
    }
    const { customer_id, balance } = this.accountForm.value;
    this.account = new AccountTS(String(customer_id), Number(balance));
  }
}