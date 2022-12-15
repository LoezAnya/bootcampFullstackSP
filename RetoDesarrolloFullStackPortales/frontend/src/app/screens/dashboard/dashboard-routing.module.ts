import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Client } from 'src/app/models/client.model';
import { ClientsComponent } from '../clients/clients.component';
import { DashboardComponent } from './dashboard.component';

const routes: Routes = [{ path: 'dashboard', component: DashboardComponent, children: [
  { path: 'clients', component: ClientsComponent }
] }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DashboardRoutingModule { }
