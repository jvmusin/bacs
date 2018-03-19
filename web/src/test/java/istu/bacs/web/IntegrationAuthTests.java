package istu.bacs.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import istu.bacs.db.user.UserPersonalDetails;
import istu.bacs.db.user.UserRepository;
import istu.bacs.web.helper.Users;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
class IntegrationAuthTests {

    private static final UserPersonalDetails user = Users.regular;

    @Autowired
    MockMvc mvc;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void clearUsers() {
        userRepository.findByUsername(user.getUser().getUsername());
    }

    @AfterEach
    void clearUsersAfter() {
        userRepository.findByUsername(user.getUser().getUsername());
    }

    @Test
    void testRegistrationAndJwtCorrectness() throws Exception {
        String userJson = new ObjectMapper().writeValueAsString(user);

        mvc.perform(post("/auth/register").contentType(APPLICATION_JSON).content(userJson))
                .andExpect(status().isOk());

        String jwt = mvc.perform(post("/auth/login").contentType(APPLICATION_JSON).content(userJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getHeader("Authorization");
        assertThat(jwt, startsWith("Bearer "));

        mvc.perform(get("/contests").header("Authorization", jwt))
                .andExpect(status().isOk());
    }
}