import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from 'src/app/services/auth.service';
import { TokenService } from 'src/app/services/token.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  isLogged = false;
  isLoggedFail = false;
  roles: string[] = [];

  loginForm: FormGroup = new FormGroup({
    username: new FormControl(String, [Validators.required]),
    password: new FormControl(String, [Validators.required]),
  });
  constructor(private tokenservice: TokenService, private authservice: AuthService, private router: Router, private fb: FormBuilder,private toastr: ToastrService) {

  }
  ngOnInit(): void {
    this.loginForm = this.fb.group({
      username: ["", Validators.required],
      password: ["", Validators.required]
    });
    if (this.tokenservice.getToken()) {
      this.isLogged = true;
      this.isLoggedFail = false;
      this.roles = this.tokenservice.getAuthorities();
    }
  }



  onLogin() {
    this.authservice.login(this.loginForm.value).subscribe({
      next: (res) => {
        this.isLogged = true;
        this.isLoggedFail = false;
        this.toastr.success("Logged in");
        this.tokenservice.setToken(res.token);
        this.tokenservice.setUsername(res.username);
        this.tokenservice.setAthorities(res.authorities);
        this.roles = res.authorities;
        window.location.reload();        
        setTimeout(() => {
          this.router.navigate(['']);
        }, 3000);
      
          
      }, error: (error) => {
        this.isLogged = false;
        this.isLoggedFail = true;
        setTimeout(() => {
          this.isLoggedFail = false;
        }, 2000);
        console.log(error);
      }

    })
  }

}


