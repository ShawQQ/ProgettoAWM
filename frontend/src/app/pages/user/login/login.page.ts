import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from "@angular/forms";
import { catchError } from 'rxjs/operators';
import { AuthToken, LoginDto } from '../user';
import { UserService } from '../user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {
	loginForm: FormGroup;
	loginFormError: string;

	constructor(
		private userService: UserService,
		private formBuilder: FormBuilder
	) { }

	ngOnInit() {
		this.loginForm = this.formBuilder.group({
			email: [''],
			password: ['']
		});
	}

	doLogin(): void {
		this.loginFormError = '';
		let loginData: LoginDto = {
			email: this.loginForm.controls.email.value,
			password: this.loginForm.controls.password.value
		}
		this.userService.login(loginData)
			.pipe(
				catchError(err => this.loginFormError = err)
			)
			.subscribe((data: AuthToken) => {
				if(!this.loginFormError){
					this.userService.saveAuthToken(data);
					location.href = "/";
				}
			})
	}
}
