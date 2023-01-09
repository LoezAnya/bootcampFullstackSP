import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Account } from '../models/account.model';
import { Observable } from 'rxjs';

const baseUrl = 'http://localhost:8090/v1/api/accounts';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  constructor(private http: HttpClient) { }


  getById(id: any): Observable<Account[]> {
    return this.http.get<Account[]>(`${baseUrl}/${id}`);
  }

  createAccount(id:any,data: Account): Observable<any> {
    return this.http.post(`${baseUrl}/${id}`, data);
  }

  updateState(id:any,data:any):Observable<any>{
    return this.http.put(`${baseUrl}/${id}`, data);
  }
}
