package com.fantasi.xxd.rabbitmq.javaapi.ttl;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author xxd
 * @date 2020/8/1 16:58
 */
public class TTLCustomer {

    private final static String EXCHANGE_NAME = "SIMPLE_EXCHANGE";
    private final static String QUEUE_NAME = "SIMPLE_QUEUE";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct", false, false, null);
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println("等待消息");
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "test.rabbit");
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("message: " + msg);
                System.out.println("consumerTag: " + consumerTag);
                System.out.println("deliveryTag: " + envelope.getDeliveryTag());
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
