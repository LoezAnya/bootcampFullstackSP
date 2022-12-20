import { Component, OnInit } from '@angular/core';
import { Client } from 'src/app/models/client.model';
import { ClientServiceService } from 'src/app/services/client-service.service';

@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styleUrls: ['./clients.component.css']
})
export class ClientsComponent implements OnInit{
  public clients?: Client[];
  constructor(private clientservice: ClientServiceService){}
  message = '';
  ngOnInit(): void {
   this.retrieveClient();
  }

  retrieveClient():void{
    this.clientservice.getAll().subscribe(
     {
      next:(data)=>{
        this.clients=data;
      }
     }
    );
  }





  addItem(item: any){
   

    if(item.email=="camp3199@hotmail.com"){
      item.email="INACTIVE";

    }else if(item.email=="INACTIVE"){
      item.email="ACTIVE";
    }else if(item.email=="ACTIVE"){
      item.email="INACTIVE";
    }   

    /*this.clientservice.updateState(item.id, item)
      .subscribe({
        next: (res) => {        
          console.log(res);
        }
      });*/
  }
    
  }

