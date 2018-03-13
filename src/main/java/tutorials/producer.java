package tutorials;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class producer {

  //声明交换器
  static String exchangeName = "hello-exchange";
  //声明路由key
  static String routingKey = "hello";

  public static void main(String[] args) throws IOException, TimeoutException {
    //创建连接工厂
    ConnectionFactory factory = new ConnectionFactory();
//    factory.setUsername("guest");
//    factory.setPassword("guest");
    //设置 RabbitMQ 地址
    factory.setHost("localhost");
    //建立到代理服务器到连接
    Connection conn = factory.newConnection();
    //获得信道
    Channel channel = conn.createChannel();
    //声明交换器
//    channel.exchangeDeclare(exchangeName, "direct", true);
    //发布消息
    byte[] messageBodyBytes = "hello world!".getBytes();
    channel.basicPublish(exchangeName, routingKey, null, messageBodyBytes);
    channel.close();
    conn.close();
  }

}
