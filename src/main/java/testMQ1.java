import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class testMQ1
{

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.220.128");
        factory.setPort(5672);
        factory.setUsername("epoint");
        factory.setPassword("epoint");
        String queueName = "testq";

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
        channel.queueDeclare(queueName, true, false, false, null);

        String msg = "Hello";
        channel.basicPublish("", queueName, null, msg.getBytes("UTF-8"));

        channel.close();
        connection.close();

    }

}
