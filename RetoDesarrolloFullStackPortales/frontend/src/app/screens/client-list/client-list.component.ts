import { Component,OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { Client } from 'src/app/models/client.model';
import { ObjectState } from 'src/app/reducers/Object.state';
import { ClientServiceService } from 'src/app/services/client-service.service';

@Component({
  selector: 'app-client-list',
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.css']
})
export class ClientListComponent implements OnInit{
  public clients: Client[]=[];
  emptyAlert=true;
  constructor(private clientservice: ClientServiceService,public store: Store<ObjectState>){}
  message = '';
  ngOnInit(): void {
   this.retrieveClient();
  }
  
  retrieveClient():void{
    this.clientservice.getAll().subscribe(
     {
      next:(data)=>{
        this.clients=data;
        this.emptyAlert=false;
      }, error: (e) => {
        if(e.status==404){
          this.emptyAlert=true;
        }
      }
     }
    );
  }


  manageClient(item:Client){
    this.store.dispatch({
      type: 'MANAGE_CLIENT',
      payload: item
    })
  }
}
