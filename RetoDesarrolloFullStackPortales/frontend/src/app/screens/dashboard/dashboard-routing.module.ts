import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard.component';
import { AboutComponent } from '../about/about.component';
import { LoginComponent } from 'src/app/auth/login/login.component';
import { SignupComponent } from 'src/app/auth/signup/signup.component';
import { UserGuardService as guard } from 'src/app/guards/user-guard.service';


const routes: Routes = [{
  path: 'dashboard', component: DashboardComponent, children: [
    { path: '', redirectTo: 'about', pathMatch: 'full' },
    { path: 'about', component: AboutComponent },
    { path: 'login', component: LoginComponent },
    { path: 'signup', component: SignupComponent },
    { path: 'clients', loadChildren: () => import('../clients/clients.module').then(m => m.ClientsModule), canActivate: [guard], data: { expectedRole: ['admin'] } }

  ]
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DashboardRoutingModule { }
