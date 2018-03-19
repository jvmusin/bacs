package istu.bacs.web.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import istu.bacs.web.model.user.FullUserInfo;
import istu.bacs.web.model.user.Login;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class Security {

    public static String login(Login login, MockMvc mvc) {
        try {
            String userJson = new ObjectMapper().writeValueAsString(login);
            return mvc.perform(post("/auth/login").contentType(APPLICATION_JSON).content(userJson))
                    .andReturn()
                    .getResponse()
                    .getHeader("Authorization");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void register(FullUserInfo user, MockMvc mvc) {
        try {
            String userJson = new ObjectMapper().writeValueAsString(user);
            mvc.perform(post("/auth/register").contentType(APPLICATION_JSON).content(userJson));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}