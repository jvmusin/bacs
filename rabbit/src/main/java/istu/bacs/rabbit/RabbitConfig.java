package istu.bacs.rabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;

import static istu.bacs.rabbit.QueueNames.*;

@Configuration
public class RabbitConfig {

    private static boolean isNotBlank(String s) {
        return s != null && !s.isEmpty();
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
        if (isNotBlank(url.getPath()))
            connectionFactory.setVirtualHost(url.getPath().replace("/", ""));
        connectionFactory.setConnectionTimeout(3000);
        connectionFactory.setRequestedHeartBeat(30);
        return connectionFactory;
    }

    @Bean(SCHEDULED_SUBMISSIONS) public Queue scheduledSubmissionsQueue() {
        return new Queue(SCHEDULED_SUBMISSIONS);
    }
    @Bean(SUBMITTED_SUBMISSIONS) public Queue submittedSubmissionsQueue() {
        return new Queue(SUBMITTED_SUBMISSIONS);
    }
    @Bean(CHECKED_SUBMISSIONS) public Queue checkedSubmissionsQueue() {
        return new Queue(CHECKED_SUBMISSIONS);
    }
}