package istu.bacs.web.security;

import io.jsonwebtoken.*;
import istu.bacs.db.user.Role;
import istu.bacs.db.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

import static istu.bacs.web.security.JWTAuthenticationToken.ANONYMOUS;
import static istu.bacs.web.security.SecurityConstants.*;
import static istu.bacs.web.security.WebSecurityUserUtils.getAuthorities;
import static java.util.stream.Collectors.toList;

@Slf4j
public class ServerSecurityContextRepositoryImpl implements ServerSecurityContextRepository {

    private static JWTAuthenticationToken getAuthentication(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replaceFirst(TOKEN_PREFIX, ""))
                    .getBody();

            List<?> authorities = body.get("authorities", List.class);
            List<Role> roles = authorities.stream()
                    .map(Object::toString)
                    .map(Role::valueOf)
                    .collect(toList());

            return new JWTAuthenticationToken(body.get("userId", Integer.class), body.getSubject(), roles);
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | ExpiredJwtException | IllegalArgumentException e) {
            log.debug("User authorization failed. Token: '{}'. Reason: {}", token, e.getMessage());
            return null;
        }
    }

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return null;
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {

        String header = exchange.getRequest().getHeaders().getFirst(HEADER_STRING);

        //noinspection ConstantConditions
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            log.debug("User authentication failed: Header is null or is incorrect: '{}'", header);
            return Mono.just(new SecurityContextImpl(ANONYMOUS));
        }

        Authentication auth = getAuthentication(header.replaceFirst(TOKEN_PREFIX, ""));
        if (auth == null)
            return Mono.just(new SecurityContextImpl(ANONYMOUS));

        User user = (User) auth.getPrincipal();
        log.debug("User authenticated: {}:'{}'", user.getUserId(), user.getUsername());
        return Mono.just(new SecurityContextImpl(auth));
    }
}
