package istu.bacs.util.rabbit;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static java.time.LocalDate.now;

@EnableRabbit
@Component
public class RabbitTest {

    public static final String MY_QUEUE = "my_queue";

    private final RabbitTemplate rabbitTemplate;

    public RabbitTest(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = MY_QUEUE)
    public void processQueue(String message) {
        System.err.println("RABBIT: " + message);
    }

//    @Scheduled(fixedRate = 1000)
    public void send() {
        rabbitTemplate.convertAndSend(MY_QUEUE, "Hello! " + now());
    }
}