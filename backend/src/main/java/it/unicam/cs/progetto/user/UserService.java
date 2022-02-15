package it.unicam.cs.progetto.user;

import it.unicam.cs.progetto.prenotation.dto.PrenotationDto;
import it.unicam.cs.progetto.user.dto.*;
import it.unicam.cs.progetto.user.exception.RegistrationDataNotValidException;
import it.unicam.cs.progetto.user.exception.UserNotValidException;
import it.unicam.cs.progetto.utils.security.BasicPasswordEncoder;
import it.unicam.cs.progetto.utils.security.IPasswordEnconder;
import it.unicam.cs.progetto.utils.security.JwtNotValidException;
import it.unicam.cs.progetto.utils.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtUtil jwtUtil;

	public String registerUser(RegistrationDto registrationData) throws RegistrationDataNotValidException {
		if(!this.validateEmail(registrationData.getEmail()) || registrationData.getEmail().isEmpty()){
			throw new RegistrationDataNotValidException("Email non valida");
		}
		if(registrationData.getPassword() == null || registrationData.getPassword().isEmpty()){
			throw new RegistrationDataNotValidException("Password assente");
		}
		if(!registrationData.getPassword().equals(registrationData.getConfirmPassword())){
			throw new RegistrationDataNotValidException("Password non combaciano");
		}

		IPasswordEnconder<PasswordEncoder> passwordEncoder = new BasicPasswordEncoder();
		UserModel userModel = new UserModel();
		userModel.setEmail(registrationData.getEmail());
		userModel.setPassword(passwordEncoder.encodeString(registrationData.getPassword()));
		userModel.setName(registrationData.getName());
		userModel.setSurname(registrationData.getSurname());
		userModel.setActive(false);

		userModel = userRepository.save(userModel);
		return jwtUtil.generateToken(userModel);
	}

	private boolean validateEmail(String email) {
		return email != null && !email.isEmpty();
	}

	private UserModel getUserByEmail(String email) throws UserNotValidException {
		return userRepository.findUserByEmail(email).orElseThrow(() -> new UserNotValidException("Non existent user with email " + email));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserModel user;
		try {
			user = getUserByEmail(username);
		} catch (UserNotValidException e) {
			throw new UsernameNotFoundException("");
		}
		return new User(user.getEmail(), user.getPassword(), new ArrayList<>());
	}

	public String confirmAccount(UserToken userToken) throws JwtNotValidException, UserNotValidException {
		String jwt = userToken.getJwt();
		UserDetails userDetails = this.loadUserByUsername(jwtUtil.extractUsername(jwt));
		if(!this.jwtUtil.validateToken(jwt, userDetails)){
			throw new JwtNotValidException("Invalid authorization token");
		}
		UserModel user = getUserByEmail(jwtUtil.extractUsername(jwt));
		user.setActive(true);
		user = userRepository.save(user);
		return jwtUtil.generateToken(user);
	}

	public String login(LoginDto loginDto) throws UserNotValidException {
		if(!validateLogin(loginDto)){
			throw new UserNotValidException("Login data not valid");
		}
		IPasswordEnconder<PasswordEncoder> passwordEncoder = new BasicPasswordEncoder();
		UserModel userModel = getUserByEmail(loginDto.getEmail());
		if(!passwordEncoder.matchPassword(loginDto.getPassword(), userModel.getPassword())){
			throw new UserNotValidException("Password does not match");
		}

		return jwtUtil.generateToken(userModel);
	}

	private boolean validateLogin(LoginDto loginDto) {
		return validateEmail(loginDto.getEmail()) && validatePassword(loginDto.getPassword());
	}

	private boolean validatePassword(String password) {
		return password != null && !password.isEmpty();
	}

	public String recoverPassword(RecoverDto recoverDto) throws UserNotValidException {
		UserModel user = getUserByEmail(recoverDto.getEmail());
		if(user == null){
			throw new UserNotValidException("Not existent user with email "+recoverDto.getEmail());
		}
		//Invio email?
		return jwtUtil.generateToken(user);
	}

	public String changePassword(UserToken userToken, RecoverDto recoverDto) throws UserNotValidException, JwtNotValidException {
		String jwt = userToken.getJwt();
		UserDetails userDetails = this.loadUserByUsername(jwtUtil.extractUsername(jwt));
		if(!this.jwtUtil.validateToken(jwt, userDetails)){
			throw new JwtNotValidException("Invalid authorization token");
		}
		UserModel user = getUserByEmail(jwtUtil.extractUsername(jwt));
		if(!this.validatePassword(recoverDto.getNewPassword())){
			throw new UserNotValidException("Invalid password");
		}
		IPasswordEnconder<PasswordEncoder> passwordEnconder = new BasicPasswordEncoder();
		user.setPassword(passwordEnconder.encodeString(recoverDto.getNewPassword()));
		user = this.userRepository.save(user);
		return jwtUtil.generateToken(user);
	}

	public UserDto getUserData(UserToken authToken) throws UserNotValidException {
		UserModel userModel = userRepository.findUserByEmail(jwtUtil.extractUsername(authToken.getJwt())).orElseThrow(() -> new UserNotValidException("Utente non valido"));
		return new UserDto(userModel.getId(), userModel.getName(), userModel.getSurname(), userModel.getEmail(), userModel.isActive(), userModel.isAdmin(), userModel.getPrenotationDto());
	}
}
