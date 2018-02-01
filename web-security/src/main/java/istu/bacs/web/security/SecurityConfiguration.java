package istu.bacs.web.security;

import istu.bacs.db.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;

import static istu.bacs.web.security.SecurityConstants.LOGIN_URL;
import static istu.bacs.web.security.SecurityConstants.REGISTER_URL;
import static org.springframework.http.HttpMethod.POST;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

    @Bean
    CorsFilter corsFilter() {
        return new CorsFilter();
    }

    @Bean
    ReactiveAuthenticationManager reactiveAuthenticationManager() {
        return new ReactiveAuthenticationManagerImpl();
    }

    @Bean
    ServerSecurityContextRepository securityContextRepository() {
        return new ServerSecurityContextRepositoryImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Primary
    ReactiveUserDetailsService reactiveUserDetailsService(UserRepository userRepository) {
        return new ReactiveUserDetailsServiceImpl(userRepository);
    }

    @Bean
    public LoginHandler loginHandler(ReactiveUserDetailsService reactiveUserDetailsService, PasswordEncoder passwordEncoder) {
        return new LoginHandler(reactiveUserDetailsService, passwordEncoder);
    }

    @Bean
    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http,
                                                ReactiveAuthenticationManager reactiveAuthenticationManager,
                                                ServerSecurityContextRepository securityContextRepository) {
        http.httpBasic().disable();
        http.formLogin().disable();
        http.csrf().disable();
        http.logout().disable();

        http.authenticationManager(reactiveAuthenticationManager);
        http.securityContextRepository(securityContextRepository);

        http.authorizeExchange()
                .pathMatchers(POST, REGISTER_URL).permitAll()
                .pathMatchers(POST, LOGIN_URL).permitAll()
                .anyExchange().authenticated();

        return http.build();
    }
}