import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HttpClientModule } from '@angular/common/http';
import { ClientListComponent } from './screens/client-list/client-list.component';
import { interceptorProvider } from './interceptor/user-interceptor.service';
import { ToastrModule } from 'ngx-toastr';

@NgModule({
  declarations: [
    AppComponent,
    ClientListComponent,
    
    
  ],
  imports: [
    ToastrModule.forRoot(),    
    BrowserAnimationsModule,
    AppRoutingModule,
    NgbModule,
    HttpClientModule
  ],
  providers: [interceptorProvider],
  bootstrap: [AppComponent]
})
export class AppModule { }
