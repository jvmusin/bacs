package istu.bacs.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.boot.jdbc.EmbeddedDatabaseConnection.H2;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = H2)
class AuthTests {

    @Autowired
    MockMvc mvc;

    @Test
    void testRegistrationAndJwtCorrectness() throws Exception {
        Map<String, Object> user = new HashMap<>();
        user.put("username", "user");
        user.put("password", "pass");
        String userJson = new ObjectMapper().writeValueAsString(user);

        mvc.perform(post("/sign-up").contentType(APPLICATION_JSON).content(userJson))
                .andExpect(status().isOk());

        String jwt = mvc.perform(post("/login").contentType(APPLICATION_JSON).content(userJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getHeader("Authorization");
        assertThat(jwt, startsWith("Bearer "));

        mvc.perform(get("/contests").header("Authorization", jwt))
                .andExpect(status().isOk());
    }
}