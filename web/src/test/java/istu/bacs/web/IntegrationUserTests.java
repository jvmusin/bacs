package istu.bacs.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import istu.bacs.db.user.UserPersonalDetails;
import istu.bacs.db.user.UserRepository;
import istu.bacs.web.helper.Security;
import istu.bacs.web.helper.Users;
import istu.bacs.web.model.user.FullUserInfo;
import istu.bacs.web.model.user.Login;
import istu.bacs.web.user.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
class IntegrationUserTests {

    private static final UserPersonalDetails user = Users.admin;
    private static final Login userLogin = Users.adminLogin;

    @Autowired
    MockMvc mvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Nested
    @DisplayName("On GET /users should")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class OnGetUsersShould {

        @BeforeAll
        void populateUsers() {
            clearUsers();
            userService.register(user);
            userRepository.save(user.getUser());
        }

        @AfterAll
        void clearUsers() {
            userRepository.deleteAll();
        }

        @Test
        @DisplayName("Return '404 Not Found' when requested non-existed user")
        void returnNotFoundWhenRequestingNonExistedUser() throws Exception {
            String jwt = Security.login(userLogin, mvc);
            mvc.perform(get("/users/somebody").header("Authorization", jwt))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Return user when requested existed user")
        void returnUserWhenRequestedExistedUser() throws Exception {
            String jwt = Security.login(userLogin, mvc);
            mvc.perform(get("/users/" + user.getUser().getUsername()).header("Authorization", jwt))
                    .andExpect(status().isOk())
                    .andExpect(content().json(new ObjectMapper().writeValueAsString(FullUserInfo.fromDb(user))));
        }
    }
}