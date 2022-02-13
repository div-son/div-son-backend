package socialnetwork.backend.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import socialnetwork.backend.model.actor.Actor;
import socialnetwork.backend.repository.actorRepository.ActorRepository;

public class CustomerUserDetailService implements UserDetailsService {

    @Autowired
    private ActorRepository actorRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Actor actor = actorRepository.findActorByEmail(email);
        return null;
    }
}
