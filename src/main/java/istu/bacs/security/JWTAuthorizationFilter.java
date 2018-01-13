package istu.bacs.security;

import io.jsonwebtoken.*;
import istu.bacs.user.User;
import lombok.extern.java.Log;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static istu.bacs.security.SecurityConstants.*;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Log
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            log.info(format("User authorized: %d:%s:%s", user.getUserId(), user.getUsername(), user.getAuthorities()));
        }

        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            try {
                Claims body = Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(token.replaceFirst(TOKEN_PREFIX, ""))
                        .getBody();

                User user = new User();
                user.setUsername(body.getSubject());
                user.setUserId(body.get("userId", Integer.class));
                List<?> authorities = body.get("authorities", List.class);
                user.setAuthorities(authorities.stream().map(Object::toString).map(SimpleGrantedAuthority::new).collect(toList()));

                return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | ExpiredJwtException | IllegalArgumentException e) {
                log.info("Unable to parse token: %s, the reason is " + e.getMessage());
            }
        } else {
            log.info("Unable to parse null token");
        }
        return null;
    }
}