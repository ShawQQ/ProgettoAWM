import { Injectable } from "@angular/core";
import { HttpClient, HttpErrorResponse, HttpHeaders } from "@angular/common/http";
import { Observable, throwError } from 'rxjs';
import { environment } from "src/environments/environment";
import { AuthToken, HeaderOption } from "../user/user";
import { catchError } from "rxjs/operators";
import { PrenotationCancelDto, PrenotationRequestDto } from "./prenotation";

@Injectable({
	providedIn: 'root',
})
export class PrenotationService{
	constructor(private http: HttpClient){};

	public getFreeSpot(authToken: AuthToken): Observable<boolean>{
		let headers = this.getHeaders({
			jwt: authToken.jwt
		});
		return this.http.post<boolean>(environment.apiBase+"prenotation/freeSpot", {}, {
			headers: headers
		})
			.pipe(
				catchError(this.handleError)
			);
	}

	public createPrenotation(request: PrenotationRequestDto, authToken: AuthToken): Observable<PrenotationRequestDto>{
		let headers = this.getHeaders({
			jwt: authToken.jwt
		});
		return this.http.put<PrenotationRequestDto>(environment.apiBase+"prenotation/doPayment", request, {
			headers: headers
		})
			.pipe(
				catchError(this.handleError)
			);
	}

	public deletePrenotation(prenotation: PrenotationCancelDto, authToken: AuthToken): Observable<boolean>{
		let headers = this.getHeaders({
			jwt: authToken.jwt
		});
		return this.http.post<boolean>(environment.apiBase+"prenotation/cancelPrenotation", prenotation, {
			headers: headers
		})
			.pipe(
				catchError(this.handleError)
			);
	}
	private handleError(error: HttpErrorResponse){
		if (error.status === 0) {
			console.error('An error occurred:', error.error);
		} else {
		console.error(
			`Backend returned code ${error.status}, body was: `, error.error);
		}
		return throwError('Something bad happened; please try again later.');
	}

	private getHeaders(option: HeaderOption): HttpHeaders{
		let headers = new HttpHeaders();
		headers = headers.append('Authorization', "Bearer "+option.jwt);
		return headers;
	}
}