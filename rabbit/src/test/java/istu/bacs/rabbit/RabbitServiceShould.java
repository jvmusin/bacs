package istu.bacs.rabbit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("RabbitService should")
class RabbitServiceShould {

    @Autowired
    RabbitService rabbitService;

    @Test
    @DisplayName("Be injectable")
    void beNotNull_whenInjected() {
        assertNotNull(rabbitService);
    }

    @Test
    @DisplayName("Return same message after pushing to the queue")
    void invokeListener_whenMessageCame() throws Exception {

        CompletableFuture<String> res = new CompletableFuture<>();
        rabbitService.subscribe(QueueName.OTHER, res::complete);

        String message = "Is Holly the best?";
        rabbitService.send(QueueName.OTHER, message);

        String result = res.get(5, TimeUnit.SECONDS);
        assertEquals(message, result);
    }
}