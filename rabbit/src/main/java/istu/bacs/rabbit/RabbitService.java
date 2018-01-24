package istu.bacs.rabbit;

import org.springframework.amqp.core.Message;

import java.util.function.Consumer;

public interface RabbitService {
    void send(QueueName queueName, Object message);
    void subscribe(QueueName queueName, Consumer<Message> consumer);
}