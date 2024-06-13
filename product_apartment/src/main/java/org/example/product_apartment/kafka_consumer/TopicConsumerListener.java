package org.example.product_apartment.kafka_consumer;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.ArrayList;
import java.util.List;



@Configuration
public class TopicConsumerListener {

    public static final String TOPIC_NAME = "topickName";

    private final List<String> massagers = new ArrayList<>();

    @KafkaListener(topics = TOPIC_NAME, groupId = "kafka-sandbox")
    public void listener(String massage) {
        synchronized (massagers) {
            massagers.add(massage);
        }
    }

    public List<String> getMassagers() {
        return massagers;
    }
}
