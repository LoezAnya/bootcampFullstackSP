import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Client } from 'src/app/models/client.model';
import { ClientsComponent } from '../clients/clients.component';
import { DashboardComponent } from './dashboard.component';
import { AboutComponent } from '../about/about.component';

const routes: Routes = [{ path: 'dashboard', component: DashboardComponent, children: [
  {path:'', redirectTo: 'about', pathMatch: 'full'},
  {path:'about',component:AboutComponent},
  // { path: 'clients', component: ClientsComponent },
  { path: 'clients', loadChildren: () => import('../clients/clients.module').then(m => m.ClientsModule) }
  
] }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DashboardRoutingModule { }
