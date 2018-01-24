package istu.bacs.web.security;

import io.jsonwebtoken.*;
import istu.bacs.db.user.Role;
import istu.bacs.db.user.User;
import istu.bacs.web.user.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static istu.bacs.web.security.SecurityConstants.*;
import static java.util.stream.Collectors.toList;

@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);

        if (header == null) {
            log.debug("User authorization failed. Authorization header is null");
            chain.doFilter(req, res);
            return;
        }

        if (!header.startsWith(TOKEN_PREFIX)) {
            log.debug("User authorization failed. Authorization header has incorrect format: '{}'", header);
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(header.replaceFirst(TOKEN_PREFIX, ""));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            log.debug("User authorized: {}:'{}':{}", user.getUserId(), user.getUsername(), user.getRoles());
        }

        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replaceFirst(TOKEN_PREFIX, ""))
                    .getBody();

            User user = new User();
            user.setUsername(body.getSubject());
            user.setUserId(body.get("userId", Integer.class));
            List<?> authorities = body.get("authorities", List.class);
            user.setRoles(authorities.stream()
                    .map(Object::toString)
                    .map(Role::valueOf)
                    .collect(toList()));

            return new UsernamePasswordAuthenticationToken(user, null, UserUtils.getAuthorities(user));
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | ExpiredJwtException | IllegalArgumentException e) {
            log.debug("User authorization failed. Token: '{}'. Reason: {}", token, e.getMessage());
            return null;
        }
    }
}