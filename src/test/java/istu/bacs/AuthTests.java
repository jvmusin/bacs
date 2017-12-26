package istu.bacs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class AuthTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void testRegistrationAndJwtCorrectness() {
        Map<String, Object> user = new HashMap<>();
        user.put("username", "user");
        user.put("password", "pass");

        ResponseEntity<Void> response = restTemplate.postForEntity("/sign-up", user, Void.class);
        assertThat(response.getStatusCode(), is(equalTo(OK)));

        String jwt = restTemplate.postForEntity("/login", user, Void.class).getHeaders().get("Authorization").get(0);
        assertThat(jwt, startsWith("Bearer "));

        RequestEntity<Void> entity = RequestEntity.get(URI.create("/contests")).header("Authorization", jwt).build();
        ResponseEntity<Object> exchange = restTemplate.exchange(entity, Object.class);
        assertThat(exchange.getStatusCode(), is(equalTo(OK)));
    }
}