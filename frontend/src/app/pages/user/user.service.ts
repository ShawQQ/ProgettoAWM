import { Injectable } from "@angular/core";
import { HttpClient, HttpErrorResponse, HttpHeaders } from "@angular/common/http";
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { LoginDto, AuthToken, RegisterDto, UserDto, HeaderOption } from "./user";
import { environment } from "src/environments/environment";

@Injectable({
	providedIn: 'root',
})
export class UserService{
	constructor(private http: HttpClient){};

	public register(registerData: RegisterDto): Observable<RegisterDto | AuthToken> {
		return this.http.post<RegisterDto>(environment.apiBase+"user/registration", registerData)
			.pipe(
				catchError(this.handleError)
			);
	}

	public login(loginData: LoginDto): Observable<LoginDto | AuthToken>{
		return this.http.post<LoginDto>(environment.apiBase+"user/login", loginData)
			.pipe(
				catchError(this.handleError)
			);
	}

	public logout(): void{
		sessionStorage.clear();
		location.reload();
	}

	public saveAuthToken(authToken: AuthToken) : void {
		sessionStorage.setItem("authToken", authToken.jwt);
	}

	public getUserData(authToken: AuthToken): Observable<UserDto>{
		let headers = this.getHeaders({
			jwt: authToken.jwt
		});
		return this.http.get<UserDto>(environment.apiBase+"user/getData", {
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