import { Component, OnInit } from '@angular/core';
import { PrenotationCancelDto, PrenotationDto, PrenotationDuration, PrenotationStatus, PrenotationTime } from '../prenotation/prenotation';
import { PrenotationService } from '../prenotation/prenotation.service';
import { AuthToken, UserDto } from '../user/user';
import { UserService } from '../user/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss'],
})
export class HomePage implements OnInit {
	isUser: boolean;
	isAdmin: boolean;
	canPrenotate: boolean;
	currentUser: UserDto;
	userPrenotation: Array<PrenotationDto>;
	constructor(
		private userService: UserService,
		private prenotationService: PrenotationService
	) { }

	ngOnInit() {
		let authToken: string = sessionStorage.getItem("authToken");
		if(authToken !== null){
			this.userService.getUserData({
				jwt: authToken
			}).subscribe((user: UserDto) => {
				this.currentUser = user;
				this.isAdmin = user.admin
				this.userPrenotation = user.prenotations;
			});
			this.prenotationService.getFreeSpot({
				jwt: authToken
			}).subscribe((result: boolean) => {
				this.canPrenotate = result;
			});
		}
		this.isUser = authToken !== null && authToken !== '';
	}

	logout(){
		this.userService.logout();
	}

	deletePrenotation(id: number){
		let prenotation: PrenotationCancelDto = {
			id: id
		};
		let authToken: AuthToken = {
			jwt: sessionStorage.getItem("authToken")
		};
		this.prenotationService.deletePrenotation(prenotation, authToken)
			.subscribe(data => {
				console.log(data);
				location.reload();
			});
	}
}
