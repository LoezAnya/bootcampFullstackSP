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
}
