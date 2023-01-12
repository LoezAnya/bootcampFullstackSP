import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder, FormArray, AbstractControl } from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';
import Validation from './validation';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {


  rolesOptions = ['user', 'admin'];

  submitted = false;
  singForm: FormGroup = new FormGroup({
    name: new FormControl(''),
    username: new FormControl(''),
    email: new FormControl(''),
    password: new FormControl(''),
    confirmPassword: new FormControl(''),
    roles: new FormArray([
      new FormControl('')
    ])
  });
  constructor(private authservice: AuthService, private fb: FormBuilder, private toastr: ToastrService) {
    this.singForm = this.fb.group({
      name: ['', Validators.required],
      username: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(20)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(40)]],
      confirmPassword: ['', Validators.required],
      roles: new FormArray([
        new FormControl('')
      ])
    },
      { validators: [Validation.match('password', 'confirmPassword')] });



  }



  roles!: FormArray;

  rolesArray: string[] = ['', ''];







  onSignUp() {

    const values = {
      name: this.singForm.get('name')?.value,
      username: this.singForm.get('username')?.value,
      email: this.singForm.get('email')?.value,
      password: this.singForm.get('password')?.value,
      roles: this.roles.value
    }

    if (this.singForm.valid) {
      this.authservice.signup(values).subscribe({
        next: (res) => {
          this.toastr.success("User registered");

        }, error: (error) => {

          this.toastr.error(error.error.message)

        }
      });
    }

  }


  get f(): { [key: string]: AbstractControl } {
    return this.singForm.controls;
  }

  onSubmit(): void {

    this.submitted = true;

    if (this.singForm.invalid) {
      return;
    }
  }







  ngOnInit(): void {
    this.roles = this.singForm.get('roles') as FormArray;
  }


}
