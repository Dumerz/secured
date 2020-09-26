package io.dumerz.secured.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static io.dumerz.secured.security.ApplicationUserRole.*;

import java.util.concurrent.TimeUnit;

import static io.dumerz.secured.security.ApplicationUserPermission.*;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfiguration(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
            .disable()
            .authorizeRequests()
            .antMatchers("/", "/css/*", "/js/*", "/images/**")
            .permitAll()
            .antMatchers(HttpMethod.DELETE, "/api/user/**").hasAuthority(USER_WRITE.getPermission())
            .antMatchers(HttpMethod.POST, "/api/user/**").hasAuthority(USER_WRITE.getPermission())
            .antMatchers(HttpMethod.PUT, "/api/user/**").hasAuthority(USER_WRITE.getPermission())
            .antMatchers(HttpMethod.GET, "/api/user/**").hasAnyRole(ADMIN.name(), ADMIN_READONLY.name())
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .loginPage("/login")
            .permitAll()
            .defaultSuccessUrl("/courses", true)
            .and()
            .rememberMe().tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(7))
            .key("somethingverysecured")
            .and()
            .logout()
            .logoutUrl("/logout")
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
            .clearAuthentication(true)
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID", "remember-me")
            .logoutSuccessUrl("/login");
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails userNew = User.builder()
            .username("user06A")
            .password(passwordEncoder.encode("P@ssword123"))
            .authorities(MEMBER.getGrantedAuthorities())
            .build();

            UserDetails userAdmin = User.builder()
            .username("user06B")
            .password(passwordEncoder.encode("P@ssword123"))
            .authorities(ADMIN.getGrantedAuthorities())
            .build();

            UserDetails userAdminReadOnly = User.builder()
            .username("user06C")
            .password(passwordEncoder.encode("P@ssword123"))
            .authorities(ADMIN_READONLY.getGrantedAuthorities())
            .build();

        return new InMemoryUserDetailsManager(
            userNew, userAdmin, userAdminReadOnly
        );
    }
}