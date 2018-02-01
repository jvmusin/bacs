package istu.bacs.web.security;

import io.jsonwebtoken.Jwts;
import istu.bacs.db.user.User;
import istu.bacs.web.model.Login;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Date;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static istu.bacs.web.security.SecurityConstants.*;
import static java.lang.System.currentTimeMillis;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Slf4j
public class LoginHandler {

    private final ReactiveUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public LoginHandler(ReactiveUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(Login.class)
                .map(this::find)
                .flatMap(this::generateToken)
                .flatMap(token -> ServerResponse.ok().header(HEADER_STRING, token).build())
                .switchIfEmpty(ServerResponse.status(UNAUTHORIZED).build());
    }

    private Mono<User> find(Login login) {
        return userDetailsService.findByUsername(login.getUsername())
                .cast(EnhancedUserDetails.class)
                .filter(ud -> passwordEncoder.matches(login.getPassword(), ud.getPassword()))
                .map(ud -> new User(
                        ud.getUserId(),
                        ud.getUsername(),
                        login.getPassword(),
                        ud.getRoles())
                );
    }

    private Mono<String> generateToken(Mono<User> user) {
        return user.doOnNext(u ->
                log.debug("User logged in {}:'{}':'{}':{}",
                        u.getUserId(),
                        u.getUsername(),
                        u.getPassword(),
                        u.getRoles()))
                .map(u -> TOKEN_PREFIX + Jwts.builder()
                        .setSubject(u.getUsername())
                        .claim("roles", u.getRoles())
                        .setExpiration(new Date(currentTimeMillis() + EXPIRATION_TIME_MILLIS))
                        .signWith(HS256, SECRET)
                        .compact());
    }
}