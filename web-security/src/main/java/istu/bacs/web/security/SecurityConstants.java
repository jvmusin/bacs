package istu.bacs.web.security;

class SecurityConstants {
    static final String SECRET = "SecretKeyToGenJWTs";                      //simple, but enough
    static final long EXPIRATION_TIME_MILLIS = 1000 * 60 * 60 * 24 * 30L;   // 30 days
    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";
    static final String REGISTER_URL = "/users";
}