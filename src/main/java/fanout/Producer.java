package fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Producer {

  private static final String EXCHANGE_NAME = "fanout-exchange-1";

  private static final String ROUTEING_KEY = "fanout-route-1";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    String message = "test exchange fanout!";
    //设置消息可持久化
    channel.basicPublish(EXCHANGE_NAME, ROUTEING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
    System.out.println(" [x] Sent '" + message + "'");

    channel.close();
    connection.close();
  }

}

