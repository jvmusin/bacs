package istu.bacs.web.security;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

public class ReactiveAuthenticationManagerImpl implements ReactiveAuthenticationManager {
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        if (authentication instanceof JWTAuthenticationToken)
            authentication.setAuthenticated(true);
        return Mono.just(authentication);
    }
}