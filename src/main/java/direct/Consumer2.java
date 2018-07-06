package direct;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

public class Consumer2 {

  //交换器名称
  private static final String EXCHANGE_NAME = "direct";
  //绑定键
  private static final String BINDING_KEY = "direct-route-2";
  //队列名称
  private static final String QUEUE_NAME = "direct-queue-2";

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
    System.out.println(" [consumer 2] bind queue '" + QUEUE_NAME);
    //接收该队列上存储的消息
    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope,
                                 AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, "UTF-8");
        System.out.println(" [consumer 2] Received routing key: " + envelope.getRoutingKey()
                           + ", exchange: " + envelope.getExchange() + ", message:" + message);
      }
    };
    channel.basicConsume(QUEUE_NAME, true, consumer);
  }

}

