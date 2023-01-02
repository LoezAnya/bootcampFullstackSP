import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HttpClientModule } from '@angular/common/http';
import { ClientDetailsComponent } from './screens/client-details/client-details.component';
import { AccountsComponent } from './screens/accounts/accounts.component';
import { TransactionsComponent } from './screens/transactions/transactions.component';
import { AboutComponent } from './screens/about/about.component';
import { FooterComponent } from './screens/footer/footer.component';
import { ClientListComponent } from './screens/client-list/client-list.component';
import { ClientCreateComponent } from './screens/client-create/client-create.component';

@NgModule({
  declarations: [
    AppComponent,
    ClientListComponent
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
