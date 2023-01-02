import { createInjectableType } from '@angular/compiler';
import { Component,OnInit } from '@angular/core';
import { Client } from 'src/app/models/client.model';
import { ClientServiceService } from 'src/app/services/client-service.service';

@Component({
  selector: 'app-client-list',
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.css']
})


export class ClientListComponent implements OnInit{
   clients: Client[]=[];
   displayedColumns: string[] = ['id', 'identificationType', 'identification', 'firstName','lastName','email','birthdate','creationDate','manage'];
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
