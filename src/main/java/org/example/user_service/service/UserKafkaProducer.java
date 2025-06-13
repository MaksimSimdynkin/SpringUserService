package org.example.user_service.service;

import org.example.user_service.dto.UserOperationMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserKafkaProducer {

    private final KafkaTemplate<String, UserOperationMessage> kafkaTemplate;

    public UserKafkaProducer(KafkaTemplate<String, UserOperationMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void senderUserOperation(String operation, String email) {
        UserOperationMessage userOperationMessage = new UserOperationMessage();
        userOperationMessage.setOperation(operation);
        userOperationMessage.setEmail(email);
        kafkaTemplate.send("user-operation-topic", userOperationMessage);
    }
}
