package org.example.user_service.service;

import org.example.user_service.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserKafkaProducer {

    private static final Logger log = LoggerFactory.getLogger(UserKafkaProducer.class);

    private final KafkaTemplate<String, User> kafkaTemplate;


    public UserKafkaProducer(KafkaTemplate<String, User> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserToKafka(User user) {
        kafkaTemplate.send("user", String.valueOf(user.getId()), user);
        log.info("Order sent to kafka: id={}", user.getId());
    }

}
