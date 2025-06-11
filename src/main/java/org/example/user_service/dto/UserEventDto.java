package org.example.user_service.dto;

public class UserEventDto {
    private String email;
    private String eventType; // "CREATED" or "DELETED"

    public UserEventDto() {
    }

    public UserEventDto(String email, String eventType) {
        this.email = email;
        this.eventType = eventType;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
