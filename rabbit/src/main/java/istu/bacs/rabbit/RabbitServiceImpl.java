package istu.bacs.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;

import java.util.function.Consumer;

class RabbitServiceImpl implements RabbitService {

    private final AmqpAdmin amqpAdmin;
    private final RabbitTemplate rabbitTemplate;
    private final ConnectionFactory connectionFactory;

    RabbitServiceImpl(AmqpAdmin amqpAdmin, RabbitTemplate rabbitTemplate, ConnectionFactory connectionFactory) {
        this.amqpAdmin = amqpAdmin;
        this.rabbitTemplate = rabbitTemplate;
        this.connectionFactory = connectionFactory;

        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    }

    @Override
    public void send(String queueName, Object message) {
        rabbitTemplate.convertAndSend(queueName, message);
    }

    @Override
    public void subscribe(String queueName, Consumer<Message> consumer) {
        Queue queue = new Queue(queueName);
        queueName = amqpAdmin.declareQueue(queue);
        amqpAdmin.declareBinding(BindingBuilder.bind(queue).to(DirectExchange.DEFAULT).with(queueName));

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(consumer::accept);
        container.setMessageConverter(new SimpleMessageConverter());
        container.start();
    }
}
