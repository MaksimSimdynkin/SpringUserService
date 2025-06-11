package org.example.user_service.kafka;


import org.example.user_service.dto.UserEventDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class UserEventProducer {

    private final KafkaTemplate<String, UserEventDto> kafkaTemplate;
    private final String userCreatedTopic;
    private final String userDeletedTopic;

    public UserEventProducer(KafkaTemplate<String, UserEventDto> kafkaTemplate,
                             @Value("${user.service.topic.user-created}") String userCreatedTopic,
                             @Value("${user.service.topic.user-deleted}") String userDeletedTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.userCreatedTopic = userCreatedTopic;
        this.userDeletedTopic = userDeletedTopic;
    }

    public void sendUserCreatedEvent(String email) {
        UserEventDto event = new UserEventDto(email, "CREATED");
        kafkaTemplate.send(userCreatedTopic, event);
    }

    public void sendUserDeletedEvent(String email) {
        UserEventDto event = new UserEventDto(email, "DELETED");
        kafkaTemplate.send(userDeletedTopic, event);
    }
}