import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';

import { ClientsRoutingModule } from './clients-routing.module';
import { ClientsComponent } from './clients.component';
import { AccountsComponent } from '../accounts/accounts.component';
import { ClientDetailsComponent } from '../client-details/client-details.component';
import { TransactionsComponent } from '../transactions/transactions.component';
import { StoreModule } from '@ngrx/store';
import { manageClients } from 'src/app/reducers/client-reducer';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ClientCreateComponent } from '../client-create/client-create.component';
import { interceptorProvider } from 'src/app/interceptor/user-interceptor.service';


@NgModule({
  declarations: [
    ClientsComponent,
    ClientDetailsComponent,
    AccountsComponent,
    TransactionsComponent,
    ClientCreateComponent
  ],
  imports: [
    CommonModule,
    ClientsRoutingModule,
    StoreModule.forRoot({ client: manageClients }),
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [DatePipe,interceptorProvider],
  bootstrap: [AccountsComponent,ClientDetailsComponent]
})
export class ClientsModule { }
