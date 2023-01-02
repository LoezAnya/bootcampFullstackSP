import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DashboardRoutingModule } from './dashboard-routing.module';
import { DashboardComponent } from './dashboard.component';
import { AboutComponent } from '../about/about.component';
import { AccountsComponent } from '../accounts/accounts.component';
import { ClientDetailsComponent } from '../client-details/client-details.component';
import { FooterComponent } from '../footer/footer.component';
import { TransactionsComponent } from '../transactions/transactions.component';


@NgModule({
  declarations: [
    DashboardComponent,
    AboutComponent,
    FooterComponent
  ],
  imports: [
    CommonModule,
    DashboardRoutingModule
  ]
})
export class DashboardModule { }
