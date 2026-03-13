package com.innoveller.smsbroker.exceptions;

public class ProblemDetails {

    private String type;
    private String title;
    private Integer status;
    private String detail;

    public ProblemDetails() {
    }

    public ProblemDetails(String type, String title, Integer status, String detail) {
        this.type = type;
        this.title = title;
        this.status = status;
        this.detail = detail;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDetail() {
        return detail;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String type;
        private String title;
        private Integer status;
        private String detail;

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder status(Integer status) {
            this.status = status;
            return this;
        }

        public Builder detail(String detail) {
            this.detail = detail;
            return this;
        }

        public ProblemDetails build() {
            return new ProblemDetails(type, title, status, detail);
        }

    }
}
