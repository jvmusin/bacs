package istu.bacs.rabbit;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SerializerMessageConverter;

import java.util.function.Consumer;

class RabbitServiceImpl implements RabbitService {

    private static final MessageConverter messageConverter = new SerializerMessageConverter();

    private final AmqpAdmin amqpAdmin;
    private final RabbitTemplate rabbitTemplate;
    private final ConnectionFactory connectionFactory;

    RabbitServiceImpl(AmqpAdmin amqpAdmin, RabbitTemplate rabbitTemplate, ConnectionFactory connectionFactory) {
        this.amqpAdmin = amqpAdmin;
        this.rabbitTemplate = rabbitTemplate;
        this.connectionFactory = connectionFactory;

        rabbitTemplate.setMessageConverter(messageConverter);
    }

    @Override
    public void send(QueueName queueName, Object message) {
        rabbitTemplate.convertAndSend(queueName.name(), message);
    }

    @Override
    public <T> void subscribe(QueueName queueName, Consumer<T> consumer) {
        Queue queue = new Queue(queueName.name());
        amqpAdmin.declareQueue(queue);
        amqpAdmin.declareBinding(BindingBuilder.bind(queue).to(DirectExchange.DEFAULT).with(queueName));

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName.name());

//        container.setConcurrentConsumers(20);

        container.setMessageListener(m -> {
            //noinspection unchecked
            consumer.accept((T) messageConverter.fromMessage(m));
        });
        container.setMessageConverter(messageConverter);
        container.start();
    }
}