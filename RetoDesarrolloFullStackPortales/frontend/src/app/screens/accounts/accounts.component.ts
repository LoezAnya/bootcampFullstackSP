import { Component, Input, OnInit } from "@angular/core";
import {
  FormArray,
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from "@angular/forms";
import { ActivatedRoute } from "@angular/router";
import { Account } from "src/app/models/account.model";
import { Client } from "src/app/models/client.model";
import { Transaction } from "src/app/models/transaction.model";
import { AccountService } from "src/app/services/account.service";
import { TransactionService } from "src/app/services/transaction.service";

@Component({
  selector: "app-accounts",
  templateUrl: "./accounts.component.html",
  styleUrls: ["./accounts.component.css"],
})
export class AccountsComponent implements OnInit {
  createdAlert = false;
  emptyAlert = false;
  movementAlert = false;
  rejectedAlert = false;
  errorAlert = false;
  modal = document.getElementById("exampleModal");
  id: number;
  account: Account;
  accounts: Account[] = [];
  transactions: Transaction[] = [];
  accountForm: FormGroup = new FormGroup({
    id: new FormControl(Number),
    account_type: new FormControl(String),
    accountnumber: new FormControl(String),
    account_state: new FormControl(String),
    balance: new FormControl(Number),
    available_balance: new FormControl(Number),
    extentGMF: new FormControl(Boolean),
    creation_date: new FormControl(Date),
    userCreate: new FormControl(String),
    modification_date: new FormControl(Date),
    user_edit: new FormControl(String),
  });

  transacctionForm: FormGroup = new FormGroup({
    id: new FormControl(Number),
    gmf: new FormControl(Boolean),
    transaction_date: new FormControl(Date),
    description: new FormControl(""),
    available_balance: new FormControl(Number),
    movement_type: new FormControl(""),
    id_sender_account: new FormControl(""),
    id_receptor_account: new FormControl(""),
    transaction_type: new FormControl(""),
    transaction_value: new FormControl(Number),
  });

  formArray: FormArray;
  constructor(
    private route: ActivatedRoute,
    private accountService: AccountService,
    private transactionService: TransactionService,
    private fb: FormBuilder
  ) {
    this.account = new Account();
    this.formArray = new FormArray([new FormGroup({})]);
    this.id = this.route.snapshot.params["id"];
  }

  setSender(accountTransaction: Account) {
    this.transacctionForm
      .get("id_sender_account")
      ?.setValue(accountTransaction.accountnumber);
  }

  setType(type: string) {
    this.transacctionForm.get("transaction_type")?.setValue(type);
  }
  makeTransaction() {
    console.log(this.transacctionForm.value);

    this.transactionService
      .makeTransaction(this.transacctionForm.value)
      .subscribe({
        next: (res) => {
          console.log(res);
          setTimeout(() => {
            this.transacctionForm.reset();
            this.retrieveAccounts();
          }, 1500);
        },
        error: (e) => {
          this.transacctionForm.reset();
          if (e.status == 404) {
            this.errorAlert = true;
          } else if (e.status == 406) {
            this.rejectedAlert = true;
          }
        },
      });
  }

  retriveMovements(item: Account) {
    console.log(item.id);
    this.transactionService.getTransactionsById(item.id).subscribe({
      next: (data) => {
        this.transactions = data;
        this.movementAlert = false;
      },
      error: (e) => {
        if (e.status == 404) {
          this.movementAlert = true;
        }
      },
    });
  }

  retrieveAccounts() {
    this.accountService.getById(this.id).subscribe({
      next: (data) => {
        this.accounts = data;
      },
      error: (e) => {
        if (e.status == 404) {
          this.emptyAlert = true;
        }
      },
    });
  }

  changeState(item: Account) {
    console.log(item.id);
    this.accountService.updateState(item.id, item).subscribe({
      next: (res) => {
        console.log(res);
        this.retrieveAccounts();
      },
      error: (e) => {
        if (e.status == 404) {
          this.emptyAlert = true;
        }
      },
    })
  }

  cancelAccount(item: Account) {
    const defaultState=item.account_state;
    console.log(defaultState);
    item.account_state="CANCELED";
    this.accountService.updateState(item.id, item).subscribe({
      next: (res) => {
        
        console.log(res);
        this.retrieveAccounts();

      },
      error: (e) => {
        this.retrieveAccounts();
        item.account_state=defaultState;
        if (e.status == 404) {
          
        }else if(e.status == 406){
          
          console.log(e.error)
        }
      },
    });
  }


  createAccount() {
    console.log(this.accountForm.value);
    this.accountService
      .createAccount(this.id, this.accountForm.value)
      .subscribe({
        next: (res) => {
          this.createdAlert = true;

          this.emptyAlert = false;

          console.log(res);
          setTimeout(() => {
            this.retrieveAccounts();
            this.createdAlert = false;
          }, 1500);
        },
        error: (e) => {
          console.log(e.status);
        },
      });
    this.formArray.push(this.accountForm);
  }
  ngOnInit(): void {
    this.accountForm.get("account_type")?.setValue("msg");
    console.log(this.id);
    this.retrieveAccounts();
  }
}
