export interface SignupRequest {
    name:string;
    username:string;
    email:string;
    password:string
    authorities:string[];

    // constructor(name:string,username:string,email:string,password:string){}
}
