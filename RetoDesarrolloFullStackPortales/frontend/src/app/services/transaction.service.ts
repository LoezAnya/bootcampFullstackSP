import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
const baseUrl = 'http://localhost:8090/v1/api/transactions';
@Injectable({
  providedIn: 'root'
})
export class TransactionService {

  constructor(private http: HttpClient) { }

  makeTransaction(data:any):Observable<any>{
    return this.http.post(baseUrl, data);
  }

  getTransactionsById(id:any):Observable<any>{
    return this.http.get(`${baseUrl}/${id}`);
  }
}
