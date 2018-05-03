package direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Producer {

  //交换器名称
  private static final String EXCHANGE_NAME = "direct";
  //绑定键
  private static final String BINDING_KEY = "direct-bind-1";
  //队列名称
  private static final String QUEUE_NAME = "direct-queue-" + System.currentTimeMillis();
  //路由键
  private static final String ROUTEING_KEY = "direct-bind-1";

  public static void main(String[] argv) throws Exception {

    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    //通过AMQP协议创建一个通信信道
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    //声明交换器
    channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
    //声明队列
    channel.queueDeclare(QUEUE_NAME, true, false, false, null);
    //通过绑定键将交换器和队列绑定在一起
    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, BINDING_KEY);
    String message = "test exchange direct";
    //发送消息，设置消息可持久化
    channel.basicPublish(EXCHANGE_NAME, ROUTEING_KEY, true,  MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
    System.out.println(" [x] Sent '" + ROUTEING_KEY + "' : '" + message + "'");
    //关闭通信
    channel.close();
    connection.close();
  }

}

