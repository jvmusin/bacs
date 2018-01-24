package istu.bacs.rabbit;

import org.springframework.amqp.core.Message;

import java.util.function.Consumer;

public interface RabbitService {
    void send(String queueName, Object message);
    void subscribe(String queueName, Consumer<Message> consumer);
}