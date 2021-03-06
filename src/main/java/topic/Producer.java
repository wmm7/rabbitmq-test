package topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Producer {

  //交换器名称
  private static final String EXCHANGE_NAME = "topic";
  //路由键
  private static final String ROUTING_KEY = "topic.route.1";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    //通过AMQP协议创建一个通信信道
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    //声明交换器
    channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
    String message = "test exchange topic!";
    //设置消息可持久化
    channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
    System.out.println("Send routing key: " + ROUTING_KEY + ", exchange: " + EXCHANGE_NAME
                       + ", message: " + message);
    //关闭通信
    channel.close();
    connection.close();
  }


}

