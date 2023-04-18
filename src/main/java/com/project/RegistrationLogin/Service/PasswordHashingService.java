package com.project.RegistrationLogin.Service;

public interface PasswordHashingService {

    String hashPassword(String password);

    Boolean verifyPassword(String password, String receivedPassword);
}
