package direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {

  private static final String EXCHANGE_NAME = "direct";

  private static final String ROUTEING_KEY = "direct-1";

  public static void main(String[] argv) throws Exception {

    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

    String message = "test exchange direct";

    channel.basicPublish(EXCHANGE_NAME, ROUTEING_KEY, null, message.getBytes("UTF-8"));
    System.out.println(" [x] Sent '" + ROUTEING_KEY + "' : '" + message + "'");

    channel.close();
    connection.close();
  }

}

