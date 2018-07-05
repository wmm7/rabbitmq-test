package dlx;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Consumer {

  //交换器名称
  private static final String EXCHANGE_NAME_DLX = "exchange-dlx";

  //绑定键
  private static final String BINDING_KEY = "route-dlx";

  //队列名称
  private static final String QUEUE_NAME = "queue-dlx";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    //通过AMQP协议创建一个通信信道
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    //声明交换器
    channel.exchangeDeclare(EXCHANGE_NAME_DLX, BuiltinExchangeType.DIRECT);
    //声明队列
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);

    //通过绑定键将交换器和队列绑定在一起
    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME_DLX, BINDING_KEY);

    System.out.println(" [consumer 1] bind queue '" + QUEUE_NAME);
    //接收该队列上存储的消息
    com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope,
                                 AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, "UTF-8");
        System.out.println(" [consumer 1] Received '" + envelope.getRoutingKey() + ", " + envelope.getExchange() + "':'" + message + "'");
      }
    };
    channel.basicConsume(QUEUE_NAME, true, consumer);
  }

}
