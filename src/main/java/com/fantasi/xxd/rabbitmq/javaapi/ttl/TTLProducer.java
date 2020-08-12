package com.fantasi.xxd.rabbitmq.javaapi.ttl;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author xxd
 * @date 2020/8/1 16:52
 */
public class TTLProducer {

    private final static String EXCHANGE_NAME = "SIMPLE_EXCHANGE";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        String msg = "Hello word";
        //通过队列属性设置消息过期时间
        Map<String,Object> params = new HashMap<>();
        params.put("x-message-ttl", 6000); //过期时间
        params.put("x-max-priority", 5);  //优先级
        channel.queueDeclare("TEST_TTL_QUEUE", false, false, false, params);
        //对每条消息设置过期时间
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2) //持久化消息
                .contentEncoding("UTF-8")
                .priority(5)  //优先级 默认5
                .expiration("10000") //TTL
                .build();
        channel.basicPublish("", "TEST_TTL_QUEUE", null, msg.getBytes());
        channel.close();
        connection.close();
    }
}
