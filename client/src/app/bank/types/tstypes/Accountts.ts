export class AccountTS {
    accountId?: string;
    customerId: string;
    balance: number;
    constructor(customerId: string, balance: number, accountId?: string) {
        this.customerId = customerId;
        this.balance = balance;
        this.accountId = accountId;
    }

    displayInfo(): void {
        console.log(`Account ID: ${this.accountId}`)
        console.log(`Customer ID: ${this.customerId}`)
        console.log(`Balance: ${this.balance.toFixed(2)}`)
    }

}