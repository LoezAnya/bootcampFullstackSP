import { DatePipe } from "@angular/common";
import { Component, OnInit } from "@angular/core";
import {
  FormGroup,
  FormBuilder,
  FormControl,
  Validators,
} from "@angular/forms";
import { Client } from "src/app/models/client.model";
import { ClientServiceService } from "src/app/services/client-service.service";

@Component({
  selector: "app-client-create",
  templateUrl: "./client-create.component.html",
  styleUrls: ["./client-create.component.css"],
})
export class ClientCreateComponent implements OnInit {
  client: Client;
  errorMessage: boolean=false;
  showAlert = false;
  clientForm: FormGroup = new FormGroup({
    id: new FormControl(Number, [Validators.required]),
    identificationType: new FormControl(String, [Validators.required]),
    identification: new FormControl(Number, [Validators.required]),
    firstName: new FormControl(String, [Validators.required]),
    lastName: new FormControl(String, [Validators.required]),
    email: new FormControl(String, [Validators.required]),
    birthdate: new FormControl(Date, [Validators.required]),
  });

  constructor(
    private fb: FormBuilder,
    public clientService: ClientServiceService,
    
  ) {
    
    this.client = new Client();
  }
  ngOnInit(): void {
    this.clientForm = this.fb.group({
      id:["", Validators.required],
      identificationType:[ "", Validators.required],
      identification: ["", Validators.required],
      firstName: ["", Validators.required],
      lastName: ["", Validators.required],
      email:["", Validators.required],
      birthdate: [Date, Validators.required],
    });

    this.clientForm.get('identificationType')?.setValue('msg');

    
      
   
  }

  saveClient(): void {
    this.clientService.createClient(this.clientForm.value).subscribe({
      next: (res) => {
        this.showAlert=true;
        setTimeout(() => {
          this.showAlert=false;
        }, 2000);
        console.log(res);
        this.clientForm.reset();
        this.errorMessage =false;
      },
      error: (e) => {
        this.errorMessage =true;
        setTimeout(() => {
          this.errorMessage=false;
        }, 2000);
      },
    });
  }
}
