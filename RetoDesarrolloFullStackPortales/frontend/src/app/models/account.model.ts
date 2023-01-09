export class Account {
    id: Number;
    account_type: String;
    account_number: String;
    account_state: String;
    balance: Number;
    available_balance: Number;
    extentGMF: Boolean;
    creation_date: Date;
    userCreate: String;
    modification_date: Date;
    user_edit: String;

    constructor(){
        this.id=new Number;
        this.account_type=new String;
        this.account_number=new String;
        this.account_state=new String;
        this.balance=new Number;
        this.available_balance=new Number;
        this.extentGMF=new Boolean;
        this.creation_date=new Date;
        this.userCreate=new String;
        this.modification_date=new Date;
        this.user_edit=new String;

    }


}