package topic;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

public class Consumer1 {

  //交换器名称
  private static final String EXCHANGE_NAME = "topic";
  //绑定键
  private static final String BINDING_KEY = "topic.route.*";
  //队列名称
  private static final String QUEUE_NAME = "topic-queue-" + System.currentTimeMillis();

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    //通过AMQP协议创建一个通信信道
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    //声明交换器（也可以通过插件在页面添加）
    channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
    //声明队列（也可以通过插件在页面添加）
    channel.queueDeclare(QUEUE_NAME, true, false, false, null);
    //通过绑定键绑定队列和交换器（也可以通过插件在页面添加）
    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, BINDING_KEY);
    System.out.println(" [consumer 1] queue name : " + QUEUE_NAME);
    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope,
                                 AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, "UTF-8");
        System.out.println(" [consumer 1] Received '" + message + "'" + ", route key : " + envelope.getRoutingKey()
                           + ", exchange : " + envelope.getExchange());
      }
    };
    channel.basicConsume(QUEUE_NAME, true, consumer);
  }
}

