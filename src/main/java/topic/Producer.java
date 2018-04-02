package topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {

  private static final String EXCHANGE_NAME = "topic";

  private static final String ROUTEING_KEY = "topic.test.1";

  public static void main(String[] argv) {
    Connection connection = null;
    Channel channel = null;
    try {
      ConnectionFactory factory = new ConnectionFactory();
      factory.setHost("localhost");

      connection = factory.newConnection();
      channel = connection.createChannel();

      channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

      String message = "test exchange topic";

      channel.basicPublish(EXCHANGE_NAME, ROUTEING_KEY, null, message.getBytes("UTF-8"));
      System.out.println(" [x] Sent '" + ROUTEING_KEY + "':'" + message + "'");

    } catch  (Exception e) {
      e.printStackTrace();
    } finally {
      if (connection != null) {
        try {
          connection.close();
        }
        catch (Exception ignore) {}
      }
    }
  }


}

