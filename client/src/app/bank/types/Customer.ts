export class Customer {
    customerId?: string;
    name: string;
    email: string;
    password: string;
    username: string;
    role?: string;

    constructor(data: any) {
        this.customerId = data.customerId;
        this.name = data.name;
        this.email = data.email;
        this.username = data.username;
        this.password = data.password;
        this.role = data.role;
    }
}





