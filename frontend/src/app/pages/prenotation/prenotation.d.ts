export const enum PrenotationDuration{
	HALF_DAY = "Mezza giornata",
	FULL_DAY = "Intera giornata"
}
export const enum PrenotationTime{
	MORNING = "Mattina",
	POMERIGGIO = "Pomeriggio"
}
export const enum PrenotationStatus{
	REGISTERED = "Registrata"
}
export interface PrenotationRequestDto{
	prenotationDate: Date,
	prenotationDuration: PrenotationDuration,
	prenotationTime: PrenotationTime
}

export interface PrenotationDto{
	id: number,
	status: PrenotationStatus,
	duration: PrenotationDuration,
	time: PrenotationTime,
	date: Date
}

export interface PrenotationCancelDto{
	id: number
}