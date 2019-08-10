package bds.security;

import bds.resource.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Component
public class GoogleTokenFilter extends AbstractPreAuthenticatedProcessingFilter {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService repoUserDetailsService;
    private final UserRepository userRepository;

    @Value("${client_id.google}")
    private String clientId;

    @Value("${id.token.header.google}")
    private String idTokenHeader;

    private GoogleIdTokenVerifier verifier;

    Logger logger = LoggerFactory.getLogger(GoogleTokenFilter.class);

    @Autowired
    public GoogleTokenFilter(AuthenticationManager authenticationManager,
                             UserDetailsService repoUserDetailsService,
                             UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.repoUserDetailsService = repoUserDetailsService;
        this.userRepository = userRepository;
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest httpServletRequest) {
        logger.info("hdr=" + idTokenHeader);
        String idTokenString = httpServletRequest.getHeader(idTokenHeader);

        if (idTokenString == null) {
            logger.info("header missing: " + idTokenHeader);
            return null;
        }

        GoogleIdToken idToken = null;
        try {
            idToken = verifier.verify(idTokenString);
        } catch (Exception e) {
            logger.error("Failed to verify", e);
        }
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            // Print user identifier
            String userId = payload.getSubject();
            logger.info("User ID: " + userId);

            UserDetails userDetails = null;

            try {
                userDetails = repoUserDetailsService.loadUserByUsername(userId);
                logger.info("user exists: " + userDetails.toString());
            } catch(UsernameNotFoundException unfex) {
                // Create the user, as valid google user, just missing from the DB
                UserProfile up = new UserProfile(
                        userId,
                        payload.getEmail(),
                        Boolean.valueOf(payload.getEmailVerified()),
                        (String) payload.get("name"),
                        (String) payload.get("picture"),
                        (String) payload.get("locale"),
                        (String) payload.get("family_name"),
                        (String) payload.get("given_name")
                );
                userRepository.addUser(up);
                userDetails = up;
                logger.info("user created: " + userDetails.toString());
                // Use or store profile information
                // ...
            }
            return userDetails;
        } else {
            logger.info("Invalid ID token.");
            return null;
        }
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest httpServletRequest) {
        return "NA";
    }

    @Override
    public void afterPropertiesSet() {
        logger.info("after prop set");
        verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(), JacksonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(clientId))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();
        setAuthenticationManager(authenticationManager);

    }
}
