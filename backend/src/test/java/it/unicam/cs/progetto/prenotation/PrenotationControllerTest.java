package it.unicam.cs.progetto.prenotation;

import it.unicam.cs.progetto.prenotation.dto.PrenotationRequestDto;
import it.unicam.cs.progetto.prenotation.enums.PrenotationDuration;
import it.unicam.cs.progetto.prenotation.enums.PrenotationStatus;
import it.unicam.cs.progetto.prenotation.enums.PrenotationTime;
import it.unicam.cs.progetto.user.UserModel;
import it.unicam.cs.progetto.user.UserRepository;
import it.unicam.cs.progetto.utils.security.BasicPasswordEncoder;
import it.unicam.cs.progetto.utils.security.IPasswordEnconder;
import it.unicam.cs.progetto.utils.security.JwtUtil;
import it.unicam.cs.progetto.utils.settings.ApplicationSetting;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestPropertySource("classpath:application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class PrenotationControllerTest {
	@Autowired
	private WebTestClient webClient;
	@Autowired
	private ApplicationSetting applicationSetting;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private PrenotationRepository prenotationRepository;

	@Test
	void freeSpot() throws ParseException {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN);
		PrenotationRequestDto requestDto = new PrenotationRequestDto(dateFormatter.parse("01/01/2020"), PrenotationDuration.FULL_DAY, PrenotationTime.MORNING);
		this.webClient
				.post()
				.uri("/api/prenotation/freeSpot")
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(requestDto))
				.exchange()
				.expectStatus().isOk();
	}

	@Test
	void doPayment() throws ParseException {
		IPasswordEnconder<PasswordEncoder> passwordEncoder = new BasicPasswordEncoder();
		String password = passwordEncoder.encodeString("123");
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN);
		UserModel testUser = new UserModel(1L, "Test", "test", "test@test.it", password, false, false, new HashSet<>(), new HashSet<>());
		PrenotationRequestDto requestDto = new PrenotationRequestDto(dateFormatter.parse("01/01/2020"), PrenotationDuration.FULL_DAY, PrenotationTime.MORNING);
		testUser = userRepository.save(testUser);
		String authToken = jwtUtil.generateToken(testUser);
		this.webClient
				.put()
				.uri("/api/prenotation/doPayment")
				.header("Authorization", "Bearer "+authToken)
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(requestDto))
				.exchange()
				.expectStatus().isOk();
	}

	@Test
	void shouldFailIfNotEnounghSpace() throws ParseException {
		IPasswordEnconder<PasswordEncoder> passwordEncoder = new BasicPasswordEncoder();
		String password = passwordEncoder.encodeString("123");
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN);
		UserModel testUser = new UserModel(1L, "Test", "test", "test@test.it", password, false, false, new HashSet<>(), new HashSet<>());
		testUser = userRepository.save(testUser);
		PrenotationRequestDto requestDto = new PrenotationRequestDto(dateFormatter.parse("01/01/2020"), PrenotationDuration.FULL_DAY, PrenotationTime.MORNING);
		for(int i = 0; i < 10; i++){
			PrenotationModel prenotationDto = new PrenotationModel(PrenotationStatus.REGISTERED, PrenotationDuration.FULL_DAY, testUser, PrenotationTime.MORNING, dateFormatter.parse("01/01/2020"));
			prenotationRepository.save(prenotationDto);
		}
		List<PrenotationModel> prenotationList = this.prenotationRepository.findAll();
		this.webClient
				.post()
				.uri("/api/prenotation/freeSpot")
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(requestDto))
				.exchange()
				.expectStatus().is4xxClientError();
	}
}