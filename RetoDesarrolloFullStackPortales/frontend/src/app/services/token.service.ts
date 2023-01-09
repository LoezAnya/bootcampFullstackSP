import { Injectable } from '@angular/core';

const TOKEN_KEY = "AuthToken";
const NAME_KEY = "AuthName";
const USERNAME_KEY = "AuthUsername";
const AUTHORITIES_KEY = "AuthAuthorities";

@Injectable({
  providedIn: 'root'
})
export class TokenService {
  roles: Array<string> = [];
  constructor() { }

  public setName(token:string){
    window.sessionStorage.removeItem(NAME_KEY);
    window.sessionStorage.setItem(NAME_KEY, token);
  }

  public getName(){
    return sessionStorage.getItem(NAME_KEY);
  }

  public setToken(token: string): void {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
  }

  public getToken() {
    return sessionStorage.getItem(TOKEN_KEY);
  }

  public setUsername(token: string): void {
    window.sessionStorage.removeItem(USERNAME_KEY);
    window.sessionStorage.setItem(USERNAME_KEY, token);
  }

  public getUsername() {
    return sessionStorage.getItem(USERNAME_KEY);
  }

  public setAthorities(token: string[]): void {
    window.sessionStorage.removeItem(AUTHORITIES_KEY);
    window.sessionStorage.setItem(AUTHORITIES_KEY, JSON.stringify(token));
  }

  public getAuthorities(): string[] {
    this.roles = []
    if (sessionStorage.getItem(AUTHORITIES_KEY)) {
      JSON.parse(sessionStorage.getItem(AUTHORITIES_KEY) ?? '{}').forEach((authority: { authority: string; }) => {
        this.roles.push(authority.authority);
      });
    }

    return this.roles;
  }

  public logout():void{
    window.sessionStorage.clear();
  }
}
