package bds.security;

import bds.resource.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RepoUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public RepoUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<UserProfile> userProfile = userRepository.getUser(login);
        return userProfile.orElseThrow( () -> new UsernameNotFoundException("User " + login + " not found."));
    }
}