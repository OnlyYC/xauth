package com.liaoyb.auth.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static String DEFAULT_OAUTH_CLIENT = "facelogin";

    public static String DEFAULT_USER_PASSWORD="123456";

    private AuthoritiesConstants() {
    }
}
