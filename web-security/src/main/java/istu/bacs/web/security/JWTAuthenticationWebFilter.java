package istu.bacs.web.security;

import io.jsonwebtoken.*;
import istu.bacs.db.user.Role;
import istu.bacs.db.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import reactor.core.publisher.Mono;

import java.util.List;

import static istu.bacs.web.security.SecurityConstants.*;
import static istu.bacs.web.security.WebSecurityUserUtils.getAuthorities;
import static java.util.stream.Collectors.toList;

@Slf4j
public class JWTAuthenticationWebFilter extends AuthenticationWebFilter {

    public JWTAuthenticationWebFilter(ReactiveAuthenticationManager authenticationManager) {
        super(authenticationManager);

        setAuthenticationConverter(exchange -> ReactiveSecurityContextHolder.getContext()
                .switchIfEmpty(Mono.<SecurityContext>fromCallable(() -> {
                    String header = exchange.getRequest().getHeaders().getFirst(HEADER_STRING);

                    //noinspection ConstantConditions
                    if (header == null) {
                        log.debug("User authorization failed. Authorization header is null");
                        return null;
                    }

                    if (!header.startsWith(TOKEN_PREFIX)) {
                        log.debug("User authorization failed. Authorization header has incorrect format: '{}'", header);
                        return null;
                    }

                    UsernamePasswordAuthenticationToken auth = getAuthentication(header.replaceFirst(TOKEN_PREFIX, ""));
                    if (auth == null) return null;

                    User user = (User) auth.getPrincipal();
                    log.debug("User authorized: {}:'{}':{}", user.getUserId(), user.getUsername(), user.getRoles());
                    return new SecurityContextImpl(auth);
                }))
                .map(SecurityContext::getAuthentication));
    }

    private static UsernamePasswordAuthenticationToken getAuthentication(String token) {
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

            User user = User.builder()
                    .userId(body.get("userId", Integer.class))
                    .username(body.getSubject())
                    .roles(roles)
                    .build();

            return new UsernamePasswordAuthenticationToken(user, null, getAuthorities(user));
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | ExpiredJwtException | IllegalArgumentException e) {
            log.debug("User authorization failed. Token: '{}'. Reason: {}", token, e.getMessage());
            return null;
        }
    }
}