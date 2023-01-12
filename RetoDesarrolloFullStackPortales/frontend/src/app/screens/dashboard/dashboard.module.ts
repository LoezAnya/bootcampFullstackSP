import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DashboardRoutingModule } from './dashboard-routing.module';
import { DashboardComponent } from './dashboard.component';
import { AboutComponent } from '../about/about.component';
import { FooterComponent } from '../footer/footer.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from 'src/app/auth/login/login.component';
import { SignupComponent } from 'src/app/auth/signup/signup.component';
import { interceptorProvider } from 'src/app/interceptor/user-interceptor.service';


@NgModule({
  declarations: [
    DashboardComponent,
    AboutComponent,
    FooterComponent,
    LoginComponent,
    SignupComponent,

  ],
  imports: [
    CommonModule,
    DashboardRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [interceptorProvider]
})
export class DashboardModule { }
