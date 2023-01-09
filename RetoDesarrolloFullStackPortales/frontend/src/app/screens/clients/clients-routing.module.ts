import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ClientsComponent } from './clients.component';
import { ClientDetailsComponent } from '../client-details/client-details.component';
import { ClientListComponent } from '../client-list/client-list.component';
import { ClientCreateComponent } from '../client-create/client-create.component';
import { TransactionsComponent } from '../transactions/transactions.component';

const routes: Routes = [{ path: '', component: ClientsComponent, children:[
  {path:'',component:ClientListComponent},
  {path:'resume/:id',component:ClientDetailsComponent},
  {path:'add',component:ClientCreateComponent},
  {path:'transactions',component:TransactionsComponent}
  ] }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ClientsRoutingModule { }
