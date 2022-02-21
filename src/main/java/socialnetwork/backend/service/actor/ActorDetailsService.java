package socialnetwork.backend.service.actor;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import socialnetwork.backend.model.actor.Actor;
import socialnetwork.backend.repository.actorRepository.ActorRepository;

import java.util.ArrayList;

@Service
public class ActorDetailsService implements UserDetailsService {
    ActorRepository actorRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Actor account = actorRepository.findActorByEmail(email);

        return new User(account.getFirstname(), account.getPassword(), new ArrayList<>());
    }
}
