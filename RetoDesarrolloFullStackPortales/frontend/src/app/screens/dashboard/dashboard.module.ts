import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DashboardRoutingModule } from './dashboard-routing.module';
import { DashboardComponent } from './dashboard.component';
import { AppModule } from "../../app.module";
import { ClientsComponent } from '../clients/clients.component';


@NgModule({
    declarations: [
        DashboardComponent,
        ClientsComponent
    ],
    imports: [
        CommonModule,
        DashboardRoutingModule
       
    ]
})
export class DashboardModule { }
