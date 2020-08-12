package com.fantasi.xxd.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

/**
 * @author xxd
 * @date 2020/8/2 16:06
 */
public class KafkaConsumerDemo extends Thread{

    private final KafkaConsumer<Integer,String> kafkaConsumer;

    public KafkaConsumerDemo(String topic){
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "127.0.0.1:9092,127.0.0.1:9093,127.0.0.1:9094");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "KafkaConsumerDemo");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.IntegerDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        kafkaConsumer = new KafkaConsumer<Integer,String>(properties);
        //消费所有分区消息
//        kafkaConsumer.subscribe(Collections.singleton(topic));
        //只消费 0分区的消息
        TopicPartition topicPartition = new TopicPartition(topic, 0);
        kafkaConsumer.assign(Arrays.asList(topicPartition));
    }

    @Override
    public void run() {
        while (true){
            ConsumerRecords<Integer, String> consumerRecords = kafkaConsumer.poll(1000);
            for (ConsumerRecord record : consumerRecords){
                System.out.println(record.partition()+ " -> 接收的消息：" + record.value());
                kafkaConsumer.commitAsync();
            }
        }
    }

    public static void main(String[] args) {
        new KafkaConsumerDemo("demo").start();
    }
}
