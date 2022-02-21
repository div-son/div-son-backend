package socialnetwork.backend.service.actor;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import socialnetwork.backend.dto.request.ActorRegistrationRequest;
import socialnetwork.backend.dto.request.AuthenticationRequest;
import socialnetwork.backend.exception.ActorAlreadyExistException;
import socialnetwork.backend.exception.GeneralException;
import socialnetwork.backend.exception.InvalidActorNameException;
import socialnetwork.backend.model.actor.Actor;
import socialnetwork.backend.model.actor.ActorType;
import socialnetwork.backend.repository.actorRepository.ActorRepository;
import socialnetwork.backend.security.JWT.util.JwtUtil;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class ActorAuthService {

    private ActorRepository actorRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtils;

    private final ActorDetailsService actorDetailsService;


    public Actor createAccount(ActorRegistrationRequest actorRegistrationRequest) throws GeneralException {
        if(actorRegistrationRequest.getEmail() == null){
            throw new GeneralException("Actor email cannot be empty.");
        }

        if (actorRegistrationRequest.getFirstname() == null) {
            throw new InvalidActorNameException("Actor first name should not be null.");
        }

        if (actorRegistrationRequest.getLastname() == null) {
            throw new InvalidActorNameException("Actor last name can't be null.");
        }

        if (actorRegistrationRequest.getPassword() == null) {
            throw new GeneralException("Actor password should not be null.");
        }

        if (actorRegistrationRequest.getPhoneNumber() == null) {
            throw new GeneralException("Phone number should not be empty.");
        }

        Actor actorExistByEmail = actorRepository.findActorByEmail(actorRegistrationRequest.getEmail());
        Actor actorExistByPhoneNumber = actorRepository.findActorByPhoneNumber(actorRegistrationRequest.getPhoneNumber());
        if(actorExistByEmail != null || actorExistByPhoneNumber != null){
            throw new ActorAlreadyExistException("This actor already exist, " +
                    "register with a different email or phone number.");
        }

        Actor actor =new Actor();

        actor.setSex(null);
        actor.setDateOfBirth(null);
        actor.setCreatedDate(LocalDate.now());
        actor.setActorType(ActorType.USER);
        actor.setAddress("");

        actor.setFirstname(actorRegistrationRequest.getFirstname());
        actor.setEmail(actorRegistrationRequest.getEmail());
        actor.setLastname(actorRegistrationRequest.getLastname());
        actor.setPassword(actorRegistrationRequest.getPassword());
        actor.setPhoneNumber(actorExistByPhoneNumber.getPhoneNumber());
        actor.setIsVerified(false);
        actor.setModifiedDate(LocalDate.now());

        actorRepository.save(actor);
        return actor;
    }

    public String login(AuthenticationRequest authenticationRequest) throws BadCredentialsException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getPassword(),
                authenticationRequest.getPhoneNumber()));

        UserDetails userDetails = actorDetailsService.loadUserByUsername(authenticationRequest.getPhoneNumber());

        return jwtUtils.generateToken(userDetails);
    }
}

