package bds.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class TokenPreauthAuthenticationManager implements AuthenticationManager {
    Logger logger = LoggerFactory.getLogger(TokenPreauthAuthenticationManager.class);

    public TokenPreauthAuthenticationManager() {
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails ud = (UserDetails)authentication.getPrincipal();
        if (ud != null && ud.isEnabled()) {
            authentication.setAuthenticated(true);
        }
        return authentication;
    }
}
