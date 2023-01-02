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


  getAll(): Observable<Client[]> {
    return this.http.get<Client[]>(baseUrl);
  }

  getById(id: any): Observable<Client> {
    return this.http.get<Client>(`${baseUrl}/${id}`);
  }

  updateClient(id: any, data: any): Observable<any> {
    return this.http.put(`${baseUrl}/${id}`, data);
  }

  createClient(data: Client): Observable<any> {
    return this.http.post(baseUrl, data);
  }


  deleteClient(id: any, data: any): Observable<any>{
    return this.http.delete(`${baseUrl}/${id}`, data);
  }
}