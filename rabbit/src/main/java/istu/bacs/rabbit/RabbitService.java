package istu.bacs.rabbit;

import java.util.function.Consumer;

public interface RabbitService {
    void send(QueueName queueName, Object message);

    <T> void subscribe(QueueName queueName, Consumer<T> consumer);
}