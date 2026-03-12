package com.innoveller.smsbroker.integrations.infobip.dtos;

import java.util.List;

public class InfoBipResponse {

    private List<Message> messages;

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public static class Message {

        private String messageId;
        private Status status;
        private String to;

        public String getMessageId() {
            return messageId;
        }

        public Status getStatus() {
            return status;
        }

        public String getTo() {
            return to;
        }

        public void setMessageId(String messageId) {
            this.messageId = messageId;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public void setTo(String to) {
            this.to = to;
        }
    }

    public static class Status {

        private String description;
        private String groupName;
        private String name;

        public String getDescription() {
            return description;
        }

        public String getGroupName() {
            return groupName;
        }

        public String getName() {
            return name;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}