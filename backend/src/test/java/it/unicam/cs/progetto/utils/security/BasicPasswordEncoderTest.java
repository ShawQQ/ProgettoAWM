package it.unicam.cs.progetto.utils.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BasicPasswordEncoderTest {

	@Test
	public void getEncoderTest(){
		BasicPasswordEncoder passwordEncoder = new BasicPasswordEncoder();
		assertNotNull(passwordEncoder.getEncoder());
	}

	@Test
	public void encodeStringTest(){
		BasicPasswordEncoder passwordEncoder = new BasicPasswordEncoder();
		String hashedString = passwordEncoder.encodeString("123");
		assertNotEquals(hashedString, "123");
		assertFalse(hashedString.isEmpty());
	}

	@Test
	public void matchPasswordTest(){
		BasicPasswordEncoder passwordEncoder = new BasicPasswordEncoder();
		String hash = passwordEncoder.encodeString("CorrectHorseBatteryStaple");
		assertTrue(passwordEncoder.matchPassword("CorrectHorseBatteryStaple", hash));
		assertFalse(passwordEncoder.matchPassword("CorrectHorseBatteryStaple", "123"));
	}
}