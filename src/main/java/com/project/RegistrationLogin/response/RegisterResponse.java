package com.project.RegistrationLogin.response;

import lombok.Builder;

@Builder
public class RegisterResponse {

    String message;

    Boolean status;

    private String token;

    public RegisterResponse(String message, Boolean status, String token) {
        this.message = message;
        this.status = status;
        this.token = token;
    }

    public RegisterResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getToken(){return token;}

    public void setToken(String token){this.token = token;}

    @Override
    public String toString() {
        return "RegisterResponse{" +
                "message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}
