package it.unicam.cs.progetto.user;

import it.unicam.cs.progetto.user.dto.LoginDto;
import it.unicam.cs.progetto.user.dto.RecoverDto;
import it.unicam.cs.progetto.user.dto.RegistrationDto;
import it.unicam.cs.progetto.user.dto.UserToken;
import it.unicam.cs.progetto.utils.security.BasicPasswordEncoder;
import it.unicam.cs.progetto.utils.security.IPasswordEnconder;
import it.unicam.cs.progetto.utils.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.HashSet;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestPropertySource("classpath:application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserControllerTest {

	@Autowired
	private WebTestClient webClient;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	JwtUtil jwtUtil;

	@Test
	void registerUser() {
		RegistrationDto testDto = new RegistrationDto("test@test.it", "123", "123", "Test", "test");
		this.webClient
				.post()
				.uri("/api/user/registration")
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(testDto))
				.exchange()
				.expectStatus().isOk();
	}

	@Test
	void shouldFailIfNotValidRegisterBody(){
		List<RegistrationDto> invalidDtos = List.of(
				new RegistrationDto("", "", "", "", ""),
				new RegistrationDto("test@test.it", "", "", "", ""),
				new RegistrationDto("test@test.it", "123", "", "", "")
		);

		for (RegistrationDto invalidDto : invalidDtos) {
			this.webClient
					.post()
					.uri("/api/user/registration")
					.contentType(MediaType.APPLICATION_JSON)
					.body(BodyInserters.fromValue(invalidDto))
					.exchange()
					.expectStatus().is4xxClientError();
		}
	}

	@Test
	void confirmAccount() {
		UserModel testUser = getTestUserModel();
		testUser = userRepository.save(testUser);
		String authToken = jwtUtil.generateToken(testUser);
		this.webClient
				.put()
				.uri("/api/user/confirmAccount")
				.header("Authorization", "Bearer "+authToken)
				.exchange()
				.expectStatus().isOk()
				.expectBody(UserToken.class);
	}

	@Test
	void login() {
		UserModel testUser = getTestUserModel();
		userRepository.save(testUser);
		LoginDto testDto = new LoginDto("test@test.it", "123");
		this.webClient
				.post()
				.uri("/api/user/login")
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(testDto))
				.exchange()
				.expectStatus().isOk()
				.expectBody(UserToken.class);
	}

	@Test
	void shouldFailIfNotValidLoginBody(){
		UserModel testUser = getTestUserModel();
		userRepository.save(testUser);
		List<LoginDto> invalidDtos = List.of(
				new LoginDto("test2@test.it", "123"),
				new LoginDto("", ""),
				new LoginDto("test@test.it", ""),
				new LoginDto("", "123"),
				new LoginDto("test@test.it", "12345678")
		);

		for (LoginDto invalidDto : invalidDtos) {
			this.webClient
					.post()
					.uri("/api/user/login")
					.contentType(MediaType.APPLICATION_JSON)
					.body(BodyInserters.fromValue(invalidDto))
					.exchange()
					.expectStatus().is4xxClientError();
		}
	}

	@Test
	void recoverPassword() {
		UserModel testUser = getTestUserModel();
		userRepository.save(testUser);
		RecoverDto testDto = new RecoverDto("test@test.it", "");
		this.webClient
				.post()
				.uri("/api/user/recover")
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(testDto))
				.exchange()
				.expectStatus().isOk()
				.expectBody(UserToken.class);

	}

	@Test
	void shouldFailIfInvalidRecoverDto(){
		UserModel testUser = getTestUserModel();
		userRepository.save(testUser);
		List<RecoverDto> invalidDtos = List.of(
				new RecoverDto("a@a.it", "1234"),
				new RecoverDto("","")
		);
		for (RecoverDto invalidDto : invalidDtos) {
			String authToken = jwtUtil.generateToken(testUser);
			this.webClient
					.post()
					.uri("/api/user/recover")
					.contentType(MediaType.APPLICATION_JSON)
					.body(BodyInserters.fromValue(invalidDto))
					.exchange()
					.expectStatus().is4xxClientError();
		}
	}

	@Test
	void changePassword() {
		UserModel testUser = getTestUserModel();
		String authToken = jwtUtil.generateToken(testUser);
		RecoverDto testDto = new RecoverDto("", "1234");
		userRepository.save(testUser);
		this.webClient
				.put()
				.uri("/api/user/changePassword")
				.header("Authorization", "Bearer "+authToken)
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(testDto))
				.exchange()
				.expectStatus().isOk()
				.expectBody(UserToken.class);
	}

	@Test
	void shouldFailIfInvalidJwtToken(){
		UserModel testUser = getTestUserModel();
		userRepository.save(testUser);
		RecoverDto testDto = new RecoverDto("test@test.it", "1234");
		this.webClient
				.put()
				.uri("/api/user/changePassword")
				.header("Authorization", "Bearer ")
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(testDto))
				.exchange()
				.expectStatus().is4xxClientError();
		this.webClient
				.put()
				.uri("/api/user/changePassword")
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(testDto))
				.exchange()
				.expectStatus().is4xxClientError();
		this.webClient
				.put()
				.uri("/api/user/confirmAccount")
				.header("Authorization", "Bearer ")
				.exchange()
				.expectStatus().is4xxClientError();
		this.webClient
				.put()
				.uri("/api/user/confirmAccount")
				.exchange()
				.expectStatus().is4xxClientError();
	}

	private UserModel getTestUserModel(){
		IPasswordEnconder<PasswordEncoder> passwordEncoder = new BasicPasswordEncoder();
		String password = passwordEncoder.encodeString("123");
		return new UserModel(1L, "Test", "test", "test@test.it", password, false, false, new HashSet<>(), new HashSet<>());
	}
}