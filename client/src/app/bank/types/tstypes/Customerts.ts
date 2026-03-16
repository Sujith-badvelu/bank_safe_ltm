export class CustomerTS {
  customerId?: string;
  name: string;
  email: string;
  username: string;
  password: string;
  role?: string;

  constructor(
    name: string,
    email: string,
    username: string,
    password: string,
    role: string,
    customerId?: string
  ) {
    this.customerId = customerId;
    this.name = name;
    this.email = email;
    this.username = username;
    this.password = password;
    this.role = role;
  }

  displayInfo(): void {
    console.log({
      customerId: this.customerId,
      name: this.name,
      email: this.email,
      username: this.username,
      password: this.password,
      role: this.role
    });
  }
}