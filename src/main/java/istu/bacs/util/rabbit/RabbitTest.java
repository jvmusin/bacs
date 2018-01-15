package istu.bacs.util.rabbit;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@EnableRabbit
@Component
public class RabbitTest {
    @RabbitListener(queues = "my_queue")
    public void processQueue(String message) {
        System.err.println("RABBIT: " + message);
    }
}