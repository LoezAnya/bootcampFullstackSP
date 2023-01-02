import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClientsRoutingModule } from './clients-routing.module';
import { ClientsComponent } from './clients.component';
import { ClientListComponent } from '../client-list/client-list.component';
import { ClientDetailsComponent } from '../client-details/client-details.component';
import { AccountsComponent } from '../accounts/accounts.component';
import { TransactionsComponent } from '../transactions/transactions.component';
import { HttpClient } from '@angular/common/http';




@NgModule({
  declarations: [
    ClientsComponent,
    ClientListComponent,
    ClientDetailsComponent,
    AccountsComponent,
    TransactionsComponent,

  ],
  imports: [
    CommonModule,
    ClientsRoutingModule
  ],
  bootstrap: [ClientsComponent,
    ClientListComponent,
    ClientDetailsComponent,
    AccountsComponent,
    TransactionsComponent,]
})
export class ClientsModule { }
