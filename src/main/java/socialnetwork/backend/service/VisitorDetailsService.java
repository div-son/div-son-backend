package socialnetwork.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import socialnetwork.backend.model.visitor.Visitor;
import socialnetwork.backend.model.visitor.VisitorType;
import socialnetwork.backend.model.visitorProfile.VisitorProfile;
import socialnetwork.backend.repository.visitorProfileRepository.VisitorProfileRepository;
import socialnetwork.backend.repository.visitorRepository.VisitorRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class VisitorDetailsService implements UserDetailsService {

    @Autowired
    VisitorProfileRepository visitorProfileRepository;

    @Override
    public UserDetails loadUserByUsername(String anyString) throws UsernameNotFoundException {
        VisitorProfile user = visitorProfileRepository.findByPhoneNumber(anyString);
        if (user == null) {
            throw new UsernameNotFoundException("Visitor does not exist.");
        }
        return new User(user.getPhoneNumber(), user.getPassword(), getAuthorities(user.getUserType()));
    }

    private Collection<GrantedAuthority> getGrantedAuthorities(VisitorType role) {

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        grantedAuthorities.add(new SimpleGrantedAuthority(String.valueOf(role)));

        return grantedAuthorities;
    }

    public Collection<? extends GrantedAuthority> getAuthorities(VisitorType role){
        return getGrantedAuthorities(role);
    }
}
