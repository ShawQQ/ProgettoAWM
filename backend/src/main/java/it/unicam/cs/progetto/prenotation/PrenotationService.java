package it.unicam.cs.progetto.prenotation;

import it.unicam.cs.progetto.prenotation.dto.PrenotationCancelDto;
import it.unicam.cs.progetto.prenotation.dto.PrenotationRequestDto;
import it.unicam.cs.progetto.prenotation.enums.PrenotationDuration;
import it.unicam.cs.progetto.prenotation.enums.PrenotationStatus;
import it.unicam.cs.progetto.prenotation.exception.NoFreeSpotException;
import it.unicam.cs.progetto.prenotation.exception.PrenotationNotValidException;
import it.unicam.cs.progetto.user.UserModel;
import it.unicam.cs.progetto.user.UserRepository;
import it.unicam.cs.progetto.user.dto.UserToken;
import it.unicam.cs.progetto.user.exception.UserNotValidException;
import it.unicam.cs.progetto.utils.security.JwtUtil;
import it.unicam.cs.progetto.utils.settings.ApplicationSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrenotationService{
    @Autowired
	private final PrenotationRepository prenotationRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final JwtUtil jwtUtil;
    @Autowired
    private ApplicationSetting applicationSetting;

    public PrenotationService(PrenotationRepository prenotationRepository, UserRepository userRepository, JwtUtil jwtUtil){
        this.prenotationRepository = prenotationRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public Boolean getFreeSpot(PrenotationRequestDto prenotationRequest) throws NoFreeSpotException {
        List<PrenotationModel> prenotationList = this.prenotationRepository.findAllPrenotationByDateAndDuration(prenotationRequest.getPrenotationDate(), prenotationRequest.getPrenotationDuration()).get();
        if(prenotationRequest.getPrenotationDuration() == PrenotationDuration.HALF_DAY){
            prenotationList = prenotationList.stream()
                                .filter( p -> p.getTime() == prenotationRequest.getPrenotationTime())
                                .collect(Collectors.toList());
        }
        int maxPrenotation = applicationSetting.getSettings().getMaxPrenotation();
        if(prenotationList.size() >= maxPrenotation){
            throw new NoFreeSpotException("Nessun ombrellone disponibile");
        }
        return true;
    }

    public void doPayment(UserToken userToken, PrenotationRequestDto prenotationRequest) throws UserNotValidException {
        //TODO: implementare pagamento
        String email = this.jwtUtil.extractUsername(userToken.getJwt());
        UserModel user = this.userRepository.findUserByEmail(email).orElseThrow(() -> new UserNotValidException("Non existent user with email" + email));
        PrenotationModel prenotation = new PrenotationModel(PrenotationStatus.REGISTERED, prenotationRequest.getPrenotationDuration(), user, prenotationRequest.getPrenotationTime(), prenotationRequest.getPrenotationDate());
        this.prenotationRepository.save(prenotation);
        user.getPrenotation().add(prenotation);
        this.userRepository.save(user);
    }

    public void cancelPrenotation(UserToken userToken, PrenotationCancelDto prenotation) throws UserNotValidException, PrenotationNotValidException {
        String userEmail = this.jwtUtil.extractUsername(userToken.getJwt());
        UserModel userModel = this.userRepository.findUserByEmail(userEmail).orElseThrow(() -> new UserNotValidException("Utente non valido"));
        PrenotationModel prenotationModel = this.prenotationRepository.getById(prenotation.getId());
        if(!prenotationModel.getUser().getId().equals(userModel.getId())){
            throw new PrenotationNotValidException("Prenotazione non valida");
        }
        userModel.getPrenotation().remove(prenotationModel);
        this.userRepository.save(userModel);
        this.prenotationRepository.delete(prenotationModel);
        //TODO: refund
    }
}