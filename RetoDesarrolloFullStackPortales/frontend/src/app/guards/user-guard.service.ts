import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { TokenService } from '../services/token.service';

@Injectable({
  providedIn: 'root'
})
export class UserGuardService implements CanActivate {
  actualRol:string="";
  constructor(private tokenService:TokenService, private router:Router) { }
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean  {
    const expectedRole=route.data['expectedRole'];
    const roles=this.tokenService.getAuthorities();
    this.actualRol='user';
    roles.forEach(rol=>{
      if(rol==='ROLE_ADMIN'){
        this.actualRol='admin';
      }
    });

    if(!this.tokenService.getToken() || expectedRole.indexOf(this.actualRol)=== -1){
      this.router.navigate(['/']);
      return false;
    }


    return true;

  }
}
