package it.unicam.cs.progetto.utils.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BasicPasswordEncoder implements IPasswordEnconder<PasswordEncoder>{
	@Override
	public PasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public String encodeString(String rawString) {
		PasswordEncoder encoder = this.getEncoder();
		return encoder.encode(rawString);
	}

	@Override
	public boolean matchPassword(String password, String hash) {
		PasswordEncoder encoder = this.getEncoder();
		return encoder.matches(password, hash);
	}
}
