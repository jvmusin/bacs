package istu.bacs.web.security;

public final class SecurityConstants {
    private SecurityConstants() {}

    public static final String SECRET = "SecretKeyToGenJWTs";   //todo: simple, but enough
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/sign-up";
}