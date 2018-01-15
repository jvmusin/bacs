package istu.bacs.util.rabbit;

import org.junit.platform.commons.util.StringUtils;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class RabbitConfig {
    @Bean
    public Queue myQ() {
        return new Queue("my_queue");
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        //получаем адрес AMQP у провайдера
        String uri = System.getenv("CLOUDAMQP_URL");
        if (uri == null) //значит мы запущены локально и нужно подключаться к локальному rabbitmq
            uri = "amqp://guest:guest@localhost";
        URI url = null;
        try {
            url = new URI(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace(); //тут ошибка крайне маловероятна
        }

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(url.getHost());
        connectionFactory.setUsername(url.getUserInfo().split(":")[0]);
        connectionFactory.setPassword(url.getUserInfo().split(":")[1]);
        if (StringUtils.isNotBlank(url.getPath()))
            connectionFactory.setVirtualHost(url.getPath().replace("/", ""));
        connectionFactory.setConnectionTimeout(3000);
        connectionFactory.setRequestedHeartBeat(30);
        return connectionFactory;
    }
}