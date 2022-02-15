package it.unicam.cs.progetto.user;

import it.unicam.cs.progetto.user.dto.*;
import it.unicam.cs.progetto.user.exception.RegistrationDataNotValidException;
import it.unicam.cs.progetto.user.exception.UserNotValidException;
import it.unicam.cs.progetto.utils.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/registration")
	public ResponseEntity<UserToken> registerUser(@RequestBody RegistrationDto registrationData){
		try{
			String userToken = userService.registerUser(registrationData);
			return this.getToken(userToken);
		}catch (RegistrationDataNotValidException e) {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage(), e);
		}
	}

	@PutMapping("/confirmAccount")
	public ResponseEntity<UserToken> confirmAccount(@RequestHeader("Authorization") String userToken){
		try{
			String newUserToken = userService.confirmAccount(jwtUtil.getUserToken(userToken));
			return this.getToken(newUserToken);
		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage(), e);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<UserToken> login(@RequestBody LoginDto loginDto){
		try{
			String userToken = userService.login(loginDto);
			return this.getToken(userToken);
		} catch (UserNotValidException e) {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage(), e);
		}
	}

	@PostMapping("/recover")
	public ResponseEntity<UserToken> recoverPassword(@RequestBody RecoverDto recoverDto) {
		try{
			String userToken = userService.recoverPassword(recoverDto);
			return this.getToken(userToken);
		} catch (UserNotValidException e) {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage(), e);
		}
	}

	@PutMapping("/changePassword")
	public ResponseEntity<UserToken> changePassword(@RequestHeader("Authorization") String userToken, @RequestBody RecoverDto recoverDto) {
		try{
			String newUserToken = userService.changePassword(jwtUtil.getUserToken(userToken), recoverDto);
			return this.getToken(newUserToken);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage(), e);
		}
	}

	@GetMapping("/getData")
	public ResponseEntity<UserDto> getData(
			@RequestHeader("Authorization") String userToken
	){
		try{
			return new ResponseEntity<>(userService.getUserData(jwtUtil.getUserToken(userToken)), HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage(), e);
		}
	}


	private ResponseEntity<UserToken> getToken(String token){
		return new ResponseEntity<>(new UserToken(token), HttpStatus.OK);
	}
}
