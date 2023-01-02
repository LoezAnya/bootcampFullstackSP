import { DatePipe } from "@angular/common";
import { Component, OnInit, AfterViewInit } from "@angular/core";
import {
  FormBuilder,
  FormGroup,
  FormControl,
  Validators,
} from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { Store } from "@ngrx/store";
import { Observable, map, tap } from "rxjs";
import { Client } from "src/app/models/client.model";
import { ObjectState } from "src/app/reducers/Object.state";
import { ClientServiceService } from "src/app/services/client-service.service";

@Component({
  selector: "app-client-details",
  templateUrl: "./client-details.component.html",
  styleUrls: ["./client-details.component.css"],
  providers: [DatePipe],
})
export class ClientDetailsComponent implements OnInit {
  clientForm: FormGroup = new FormGroup({
    id: new FormControl(Number, [Validators.required]),
    identificationType: new FormControl(String, [Validators.required]),
    identification: new FormControl(Number, [Validators.required]),
    firstName: new FormControl(String, [Validators.required]),
    lastName: new FormControl(String, [Validators.required]),
    email: new FormControl(String, [Validators.required]),
    birthdate: new FormControl(Date, [Validators.required]),
  });
  client: Client;
  editAlert = false;
  deleteAlert = false;
  constructor(
    private fb: FormBuilder,
    public clientService: ClientServiceService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.client = new Client();
  }

  fillForm(client: Client) {
    this.clientForm.setValue({
      id: client.id,
      identificationType: client.identificationType,
      identification: client.identification,
      firstName: client.firstName,
      lastName: client.lastName,
      email: client.email,
      birthdate: client.birthdate,
    });
  }

  getClient(): void {
    this.clientService.getById(this.route.snapshot.params["id"]).subscribe({
      next: (data) => {
        this.fillForm(data);
      },
      error: (e) => console.error(e.code),
    });
  }

  updateClient() {
    this.clientService
      .updateClient(this.route.snapshot.params["id"], this.clientForm.value)
      .subscribe({
        next: () => {
          this.editAlert = true;
          setTimeout(() => {
            this.editAlert = false;
          }, 1000);
        },
        error: (e) => console.error(e.code),
      });
  }

  deleteClient() {
    this.clientService
      .deleteClient(this.route.snapshot.params["id"], this.clientForm.value)
      .subscribe({
        next: () => {
          this.editAlert = false;
          this.deleteAlert = true;

          setTimeout(() => {
            this.router.navigate(["/clients"]);
          }, 1000);
        },
        error: (e) => console.error(e.code),
      });
  }

  ngOnInit(): void {
    this.getClient();
  }
}
