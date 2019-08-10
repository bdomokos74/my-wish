package bds.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private UserDetailsService repoUserDetailsService;
    @Autowired
    public void setRepoUserDetailsService(UserDetailsService repoUserDetailsService) {
        this.repoUserDetailsService = repoUserDetailsService;
    }

    private GoogleTokenFilter googleTokenFilter;
    @Autowired
    public void setGoogleTokenFilter(GoogleTokenFilter googleTokenFilter) {
        this.googleTokenFilter = googleTokenFilter;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(repoUserDetailsService);
        auth.authenticationProvider(provider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/*")
        .csrf()
            .disable()
        .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .addFilter(googleTokenFilter)
        .authorizeRequests()
            .anyRequest()
            .authenticated();
    }
}
