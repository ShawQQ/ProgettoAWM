import { PrenotationDto } from "../prenotation/prenotation";

export interface LoginDto{
	email: string,
	password: string
}

export interface RegisterDto{
	email: string,
	password: string,
	confirmPassword: string,
	name: string,
	surname: string
}

export interface AuthToken{
	jwt: string
}

export interface UserDto{
	id: Number,
	name: string,
	surname: string,
	email: string,
	isActive: boolean,
	admin: boolean,
	prenotations: Array<PrenotationDto>
}

export interface HeaderOption{
	jwt: string
}