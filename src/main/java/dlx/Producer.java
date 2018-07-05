package dlx;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.util.HashMap;
import java.util.Map;

public class Producer {

  //交换器名称
  private static final String EXCHANGE_NAME = "exchange-dlx-origin";

  //交换器名称
  private static final String EXCHANGE_NAME_DLX = "exchange-dlx";

  //路由键
  private static final String ROUTING_KEY = "route-dlx";

  //队列名称
  private static final String QUEUE_NAME = "queue-dlx-origin";

  private static final long TTL = 5000;

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    //声明交换器
    channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

    //声明死信队列
    Map<String, Object> args = new HashMap<String, Object>();
    args.put("x-message-ttl", TTL);
    args.put("x-dead-letter-exchange", EXCHANGE_NAME_DLX);
    channel.queueDeclare(QUEUE_NAME, false, false, false, args);

    //通过绑定键将原交换器和队列绑定在一起
    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

    String message = "test exchange dlx!";
    //发布消息
    channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN,
                         message.getBytes("UTF-8"));
    System.out.println("Sent routing key: " + ROUTING_KEY + ", exchange: " + EXCHANGE_NAME
                       + ", message: " + message);
    //关闭通信
    channel.close();
    connection.close();
  }

}
