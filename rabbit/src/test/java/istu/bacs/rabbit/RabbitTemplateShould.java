package istu.bacs.rabbit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("RabbitTemplate should")
class RabbitTemplateShould {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    @DisplayName("Be injectable")
    void beInjectable() {
        assertNotNull(rabbitTemplate);
    }

    @Test
    @DisplayName("Return same message after pushing it to a queue")
    void returnSameMessage_afterPushingToQueue(@Autowired AmqpAdmin amqpAdmin) {

        //Declare a queue that will be auto-deleted after test execution
        Queue testQueue = new Queue("Test Queue", false, true, true);
        amqpAdmin.declareQueue(testQueue);

        String now = LocalDateTime.now() + "";

        rabbitTemplate.convertAndSend(testQueue.getName(), now);
        Message message = rabbitTemplate.receive(testQueue.getName());

        assertNotNull(message.getBody());
        assertEquals(now, new String(message.getBody()));
    }
}