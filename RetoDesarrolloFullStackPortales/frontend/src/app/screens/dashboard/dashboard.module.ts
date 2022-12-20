import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DashboardRoutingModule } from './dashboard-routing.module';
import { DashboardComponent } from './dashboard.component';
import { AppModule } from "../../app.module";
import { ClientsComponent } from '../clients/clients.component';
import { ClientsDetailsComponent } from '../clients-details/clients-details.component';
import { AccountsComponent } from '../accounts/accounts.component';
import { TransactionsComponent } from '../transactions/transactions.component';


@NgModule({
    declarations: [
        DashboardComponent,
        ClientsComponent,
        ClientsDetailsComponent,
        AccountsComponent,
        TransactionsComponent
    ],
    imports: [
        CommonModule,
        DashboardRoutingModule
       
    ]
})
export class DashboardModule { }
