import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { Router } from '@angular/router';
import { TokenService } from 'src/app/services/token.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
 
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit{
  
 isLogged=false;

	constructor(private tokenservice:TokenService,private router: Router) {
    
  }

  reloadPage() {
    window.location.reload();
  }

  ngOnInit(): void {
  
    if(this.tokenservice.getToken()){
      
      this.isLogged=true;
    }else{
      this.isLogged=false;
    }
  }

  onLogOut(){
    this.tokenservice.logout();
    this.router.navigate(['/dashboard']);
    window.location.reload();
  }


}
