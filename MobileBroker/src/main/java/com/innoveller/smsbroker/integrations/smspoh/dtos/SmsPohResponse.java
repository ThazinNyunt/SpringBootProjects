package com.innoveller.smsbroker.integrations.smspoh.dtos;

import java.util.List;

public class SmsPohResponse {

    private List<Message> messages;

    public List<Message> getMessages() {
        return messages;
    }

    public static class Message {
        private String messageId;
        private String message;
        private String to;
        private String createdAt;
        private String from;
        private String network;
        private String status;

        public String getMessageId() {
            return messageId;
        }
        public String getMessage() {
            return message;
        }
        public String getTo() {
            return to;
        }
        public String getCreatedAt() {
            return createdAt;
        }
        public String getFrom() {
            return from;
        }
        public String getNetwork() {
            return network;
        }
        public String getStatus() {
            return status;
        }
    }
}
