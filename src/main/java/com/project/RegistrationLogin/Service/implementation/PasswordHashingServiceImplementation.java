package com.project.RegistrationLogin.Service.implementation;

import com.project.RegistrationLogin.Service.PasswordHashingService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;


@Service
public class PasswordHashingServiceImplementation implements PasswordHashingService {

    @Override
    public String hashPassword(String password) {
        String hashedPass = BCrypt.hashpw(password, "TEST_SALT");
        System.out.println(password);
        System.out.println(hashedPass);
        return hashedPass;
    }

    @Override
    public Boolean verifyPassword(String password, String receivedPassword) {
        Boolean matched = BCrypt.checkpw(password, receivedPassword);
        System.out.println(matched);
        return matched;
    }
}
