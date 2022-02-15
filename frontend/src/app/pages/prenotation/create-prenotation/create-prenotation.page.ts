import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidationErrors, ValidatorFn } from '@angular/forms';
import { AuthToken } from '../../user/user';
import { PrenotationRequestDto } from '../prenotation';
import { PrenotationService } from '../prenotation.service';

@Component({
  selector: 'app-create-prenotation',
  templateUrl: './create-prenotation.page.html',
  styleUrls: ['./create-prenotation.page.scss'],
})
export class CreatePrenotationPage implements OnInit {
	prenotationForm: FormGroup;
	formCompiledValidator: ValidatorFn = (control: AbstractControl) : ValidationErrors => {
		let requiredField = {
			date: control.get('date').value,
			duration: control.get('duration').value,
			time: control.get('time').value
		};
		let isInvalid: boolean = requiredField.date === '' || requiredField.duration === '';
		if(requiredField.duration == 'HALF_DAY'){
			isInvalid ||= requiredField.time === '';
		}
		return {
			isInvalid: isInvalid
		};
	}
	constructor(
		private prenotationService: PrenotationService,
		private formBuilder: FormBuilder
	) { }

	ngOnInit() {
		this.prenotationForm = this.formBuilder.group({
			date: [''],
			duration: [''],
			time: [null]
		}, {
			validators: [
				this.formCompiledValidator
			]
		});
	}

	submitPrenotation(): void {
		let authToken: AuthToken = {
			jwt: sessionStorage.getItem("authToken")
		};
		let prenotationData: PrenotationRequestDto = {
			prenotationDate: this.prenotationForm.controls.date.value,
			prenotationDuration: this.prenotationForm.controls.duration.value,
			prenotationTime: this.prenotationForm.controls.time.value
		};
		this.prenotationService.createPrenotation(prenotationData, authToken)
			.subscribe(() => {
				window.location.href = "/";
			});
	}
}
