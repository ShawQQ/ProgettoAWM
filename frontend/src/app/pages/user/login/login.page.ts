import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from "@angular/forms";
import { AuthToken, LoginDto } from '../user';
import { UserService } from '../user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {
	loginForm: FormGroup;

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
		let loginData: LoginDto = {
			email: this.loginForm.controls.email.value,
			password: this.loginForm.controls.password.value
		}
		this.userService.login(loginData)
			.subscribe((data: AuthToken) => {
				this.userService.saveAuthToken(data);
				location.href = "/";
			});
	}
}
