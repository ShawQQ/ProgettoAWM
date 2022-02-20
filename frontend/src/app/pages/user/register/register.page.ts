import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidationErrors, ValidatorFn } from '@angular/forms';
import { catchError } from 'rxjs/operators';
import { AuthToken, RegisterDto } from '../user';
import { UserService } from '../user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.page.html',
  styleUrls: ['./register.page.scss'],
})
export class RegisterPage implements OnInit {
	registerForm: FormGroup;
	registerFormError: string;
	passwordMatchValidator: ValidatorFn = (control: AbstractControl) : ValidationErrors => {
		const password = control.get('password');
		const confirmPassword = control.get('confirmPassword');
		return {
			passwordNotMatch: password.value !== confirmPassword.value
		};
	}
	formCompiledValidator: ValidatorFn = (control: AbstractControl) : ValidationErrors => {
		const requiredField = {
			email: control.get("email").value,
			password: control.get("password").value,
			confirmPassword: control.get("confirmPassword").value
		};
		return {
			requiredFieldNotCompiled: Object.values(requiredField).some(field => field === null || field === '') 
		}
	}

	constructor(
		private userService: UserService,
		private formBuilder: FormBuilder
	) { }

	ngOnInit() {
		this.registerForm = this.formBuilder.group({
			email: [''],
			password: [''],
			confirmPassword: [''],
			name: [''],
			surname: ['']
		}, {
			validators: [
				this.passwordMatchValidator,
				this.formCompiledValidator
			]
		});
	}

	doRegister(): void{
		this.registerFormError = '';
		let registerData: RegisterDto = {
			email: this.registerForm.controls.email.value,
			password: this.registerForm.controls.password.value,
			confirmPassword: this.registerForm.controls.confirmPassword.value,
			name: this.registerForm.controls.name.value,
			surname: this.registerForm.controls.surname.value
		};
		this.userService.register(registerData)
			.pipe(
				catchError(error => this.registerFormError = error)
			)
			.subscribe((data: AuthToken) => {
				if(!this.registerFormError){
					this.userService.saveAuthToken(data);
					window.location.href = "/";
				}
			});
	}
}
