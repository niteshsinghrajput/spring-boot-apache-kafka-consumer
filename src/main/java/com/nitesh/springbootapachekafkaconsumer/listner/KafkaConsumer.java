package com.nitesh.springbootapachekafkaconsumer.listner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @Value("${kafka.topic}")
    private String topicName;

    @KafkaListener(topics = "${kafka.topic}", groupId = "${kafka.consumer.group-id}")
    public void consume(String message) {
        System.out.println("Received Message ="+ message + "from topic "+topicName);
    }
}
