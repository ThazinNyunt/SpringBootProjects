package com.innoveller.smsbroker.controllers.api.dtos;

public class APIKeyTenantRequest {

    private String name;
    private String newName;

    public APIKeyTenantRequest(String name, String newName) {
        this.name = name;
        this.newName = newName;
    }

    public String getName() {
        return name;
    }

    public String getNewName() {
        return newName;
    }
}
