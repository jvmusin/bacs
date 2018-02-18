package istu.bacs.web.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import istu.bacs.db.user.User;
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
import java.util.ArrayList;
import java.util.List;

import static istu.bacs.web.security.SecurityConstants.*;
import static istu.bacs.web.security.WebSecurityUserUtils.getAuthorities;

@Slf4j
class JWTAuthorizationFilter extends BasicAuthenticationFilter {

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

        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = (User) authentication.getPrincipal();
            log.debug("User authorized: {}:'{}':{}", user.getUserId(), user.getUsername(), user.getRoles());
        }

        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();

            List<String> roles = body.get("roles", ArrayList.class);

            User user = User.builder()
                    .userId(body.get("userId", Integer.class))
                    .username(body.getSubject())
                    .roles(roles.toArray(new String[0]))
                    .build();

            return new UsernamePasswordAuthenticationToken(user, null, getAuthorities(user));
        } catch (Exception e) {
            log.debug("User authorization failed. Token: '{}'. Reason: {}", token, e.getMessage());
            return null;
        }
    }
}