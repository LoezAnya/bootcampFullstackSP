import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Client } from '../models/client.model';

@Injectable({
  providedIn: 'root'
})
export class ClientServiceService {
  public baseUrl = 'http://localhost:8090/v0/api/clients';

  constructor(private http: HttpClient) { }


  getAll(): Observable<Object[]> {
    return this.http.get<Object[]>(this.baseUrl);
  }
}
