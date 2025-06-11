package org.example.user_service.service;

import org.example.user_service.entity.User;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserKafkaProducer {

    private final KafkaTemplate<String, User> kafkaTemplate;


    public UserKafkaProducer(KafkaTemplate<String, User> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserToKafka(User user) {
        kafkaTemplate.send("user", user);
    }

}
