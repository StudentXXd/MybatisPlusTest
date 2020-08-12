package com.fantasi.xxd.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author xxd
 * @date 2020/8/1 23:51
 */
public class JMSTopicConsumer2 {

    public static void main(String[] args) {
        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            // 创建目的地
            Destination destination = session.createTopic("myTopic");
            // 创建接收者
            MessageConsumer consumer = session.createConsumer(destination);
            // 接收发送的消息
            TextMessage message = (TextMessage)consumer.receive();
            System.out.println(message.getText());
            session.commit();
            session.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            if (connection != null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
