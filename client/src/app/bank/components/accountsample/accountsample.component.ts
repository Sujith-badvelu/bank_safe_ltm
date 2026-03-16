import { Component } from '@angular/core';
import { AccountTS } from '../../types/tstypes/Accountts';

@Component({
  selector: 'app-accountsample',
  standalone: true,
  imports: [],
  templateUrl: './accountsample.component.html',
  styleUrls: ['./accountsample.component.css']
})
export class AccountsampleComponent {
  account: AccountTS;
  constructor() {
    this.account = new AccountTS("101", 30000, "1")
  }
}
