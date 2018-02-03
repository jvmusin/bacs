package istu.bacs.web.rest;

import istu.bacs.db.user.User;
import istu.bacs.web.security.JWTAuthenticationToken;
import org.omg.CORBA.ServerRequest;

import java.security.Principal;

public class AuthUtils {

    public static User getCurrentUser(Principal principal) {
        return ((JWTAuthenticationToken) principal).getPrincipal();
    }
}