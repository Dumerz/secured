package io.dumerz.secured.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import io.dumerz.secured.auth.ApplicationUserService;
import io.dumerz.secured.jwt.JwtUsernameAndPasswordAuthenticationFilter;

import org.springframework.security.crypto.password.PasswordEncoder;

import static io.dumerz.secured.security.ApplicationUserRole.*;

import static io.dumerz.secured.security.ApplicationUserPermission.*;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;

    @Autowired
    public ApplicationSecurityConfiguration(PasswordEncoder passwordEncoder,
            ApplicationUserService applicationUserService) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserService = applicationUserService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager()))
            .authorizeRequests()
            .antMatchers("/", "/css/*", "/js/*", "/images/**").permitAll()
            .antMatchers(HttpMethod.DELETE, "/api/user/**").hasAuthority(USER_WRITE.getPermission())
            .antMatchers(HttpMethod.POST, "/api/user/**").hasAuthority(USER_WRITE.getPermission())
            .antMatchers(HttpMethod.PUT, "/api/user/**").hasAuthority(USER_WRITE.getPermission())
            .antMatchers(HttpMethod.GET, "/api/user/**").hasAnyRole(ADMIN.name(), ADMIN_READONLY.name())
            .anyRequest()
            .authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }
    
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }
}