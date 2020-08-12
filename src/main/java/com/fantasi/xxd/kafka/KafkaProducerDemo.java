package com.fantasi.xxd.kafka;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @author xxd
 * @date 2020/8/2 15:54
 */
public class KafkaProducerDemo extends Thread{

    private final KafkaProducer<Integer,String> producer;

    private final boolean isAsync;

    private final String topic;

    public KafkaProducerDemo(String topic, boolean isAsync){
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "127.0.0.1:9092,127.0.0.1:9093,127.0.0.1:9094");
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaProducerDemo");
        properties.put(ProducerConfig.ACKS_CONFIG, "-1");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.IntegerSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,
                "com.fantasi.xxd.kafka.PartitionDemo");
        producer = new KafkaProducer<Integer, String>(properties);
        this.topic = topic;
        this.isAsync = isAsync;
    }

    @Override
    public void run() {
        int num = 0;
        while (num < 50){
            String message = "message_" + num;
            System.out.println("开始发送消息：" + message);
            //异步发送
            if (isAsync){
                producer.send(new ProducerRecord<Integer, String>(topic, message), new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata metadata, Exception exception) {
                        if (metadata != null){
                            System.out.println("async-offset: " + metadata.offset() +
                                    " -> partition" + metadata.partition());
                        }
                    }
                });
            }
            //同步发送  future/callback  get()阻塞方法
            else {
                try {
                    RecordMetadata metadata = producer.send(
                            new ProducerRecord<Integer, String>(topic, message)).get();
                    System.out.println("async-offset: " + metadata.offset() +
                            " -> partition: " + metadata.partition());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            num++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new KafkaProducerDemo("demo", true).start();
    }
}
