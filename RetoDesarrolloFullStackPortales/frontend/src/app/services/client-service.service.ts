import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Client } from '../models/client.model';
const baseUrl = 'http://localhost:8090/v1/api/clients';
@Injectable({
  providedIn: 'root'
})
export class ClientServiceService {
 

  constructor(private http: HttpClient) { }


  getAll(): Observable<Object[]> {
    return this.http.get<Object[]>(baseUrl);
  }

  updateState(id: any, data: any): Observable<any> {
    return this.http.put(`${baseUrl}/${id}`, data);
  }
}
