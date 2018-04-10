package direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Producer {

  private static final String EXCHANGE_NAME = "direct";

  private static final String ROUTEING_KEY = "direct-1";

  private static final String BINDING_KEY = "bind-1";

  private static final String QUEUE_NAME = "queue-1";

  public static void main(String[] argv) throws Exception {

    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

    channel.queueDeclare(QUEUE_NAME, true, false, false, null);
    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, BINDING_KEY);

    String message = "test exchange direct";
    //设置消息可持久化
    channel.basicPublish(EXCHANGE_NAME, ROUTEING_KEY,  MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
    System.out.println(" [x] Sent '" + ROUTEING_KEY + "' : '" + message + "'");

    channel.close();
    connection.close();
  }

}

