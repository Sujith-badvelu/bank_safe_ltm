import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { Customer } from "../../bank/types/Customer"
import { environment } from "../../../environments/environment";

@Injectable({
  providedIn: "root",
})
export class AuthService {
  private loginUrl = `${environment.apiUrl}`;


  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*'
    })
  };

  constructor(private http: HttpClient) { }

  login(user: Partial<Customer>): Observable<{ [key: string]: string }> {
    return this.http.post<{ token: string }>(
      `${this.loginUrl}/customer/login`,
      user,
      this.httpOptions
    );
  }

  getToken() {
    return localStorage.getItem("token");
  }
  getRole() {
    return localStorage.getItem("role");
  }


  createUser(user: Customer): Observable<Customer> {
    return this.http.post<Customer>(`${this.loginUrl}/customer/register`, user);
  }
}
