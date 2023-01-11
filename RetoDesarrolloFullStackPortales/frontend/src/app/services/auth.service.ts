import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SignupRequest } from '../models/signup-request.model';
import { Observable } from 'rxjs';
import { LoginRequest } from '../models/login-request.model';
import { JwtDto } from '../models/jwt-dto.model';
const baseUrl = 'http://localhost:8090/v1/api/auth';
@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  public signup(signuprequest:any): Observable<any> {
    return this.http.post(`${baseUrl}/signup`, signuprequest);
  }

  public login(loginrequest:LoginRequest):Observable<JwtDto>{
    return this.http.post<JwtDto>(`${baseUrl}/login`,loginrequest);
  }
}
