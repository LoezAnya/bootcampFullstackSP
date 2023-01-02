import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },  
{ path: '', loadChildren: () => import('./screens/dashboard/dashboard.module').then(m => m.DashboardModule) },
{ path: 'clients', loadChildren: () => import('./screens/clients/clients.module').then(m => m.ClientsModule) }];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
