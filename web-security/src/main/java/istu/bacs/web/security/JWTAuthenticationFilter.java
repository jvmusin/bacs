package istu.bacs.web.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import istu.bacs.web.model.user.Login;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static istu.bacs.web.security.SecurityConstants.*;
import static java.lang.System.currentTimeMillis;
import static java.util.stream.Collectors.toList;

@Slf4j
class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) {
        try {
            Login login = new ObjectMapper().readValue(req.getInputStream(), Login.class);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    login.getUsername(),
                    login.getPassword()
            );

            log.debug("User attempts to login '{}':'{}'", login.getUsername(), login.getPassword());

            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            log.debug("User authentication failed: {}", e.getMessage());
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) {

        EnhancedUserDetails user = (EnhancedUserDetails) auth.getPrincipal();
        List<String> authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(toList());

        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .claim("userId", user.getUserId())
                .claim("roles", authorities)
                .setExpiration(new Date(currentTimeMillis() + EXPIRATION_TIME_MILLIS))
                .signWith(HS256, SECRET)
                .compact();
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);

        log.debug("User logged in {}:'{}':{}", user.getUserId(), user.getUsername(), authorities);
    }
}