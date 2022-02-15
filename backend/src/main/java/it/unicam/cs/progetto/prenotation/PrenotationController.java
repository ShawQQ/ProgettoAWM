package it.unicam.cs.progetto.prenotation;

import it.unicam.cs.progetto.prenotation.dto.PrenotationCancelDto;
import it.unicam.cs.progetto.prenotation.dto.PrenotationRequestDto;
import it.unicam.cs.progetto.prenotation.exception.NoFreeSpotException;
import it.unicam.cs.progetto.prenotation.exception.PrenotationNotValidException;
import it.unicam.cs.progetto.user.exception.UserNotValidException;
import it.unicam.cs.progetto.utils.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api/prenotation")
public class PrenotationController {
	@Autowired
	private final PrenotationService prenotationService;
	@Autowired
	private final JwtUtil jwtUtil;

	public PrenotationController(PrenotationService prenotationService, JwtUtil jwtUtil) {
		this.prenotationService = prenotationService;
		this.jwtUtil = jwtUtil;
	}

	@PostMapping("/freeSpot")
	public ResponseEntity<Boolean> freeSpot(
		@RequestBody PrenotationRequestDto prenotationRequest
	){
		try{
			Boolean freeSpot = this.prenotationService.getFreeSpot(prenotationRequest);
			if(freeSpot){
				return new ResponseEntity<>(true, HttpStatus.OK);
			}
			return null;
		}catch(NoFreeSpotException e){
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage(), e);
		}
	}

	@PutMapping("/doPayment")
	public ResponseEntity<Boolean> doPayment(
		@RequestHeader("Authorization") String userToken,
		@RequestBody PrenotationRequestDto prenotationRequest
	){
		try{
			this.prenotationService.doPayment(jwtUtil.getUserToken(userToken), prenotationRequest);
			return new ResponseEntity<>(true, HttpStatus.OK);
		}catch(Exception e){
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage(), e);
		}
	}

	@PostMapping("/cancelPrenotation")
	public ResponseEntity<Boolean> cancelPrenotation(
			@RequestHeader("Authorization") String userToken,
			@RequestBody PrenotationCancelDto prenotation
	){
		try{
			this.prenotationService.cancelPrenotation(jwtUtil.getUserToken(userToken), prenotation);
			return new ResponseEntity<>(true, HttpStatus.OK);
		}catch(PrenotationNotValidException | UserNotValidException e){
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage(), e);
		}
	}
}
