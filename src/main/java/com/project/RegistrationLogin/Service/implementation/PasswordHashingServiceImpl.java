package com.project.RegistrationLogin.Service.implementation;

import com.project.RegistrationLogin.Service.PasswordHashingService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class PasswordHashingServiceImpl implements PasswordHashingService {

    @Override
    public String hashPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        String result = encoder.encode(password);
        System.out.println(result);
        return result;
    }

    @Override
    public Boolean verifyPassword(String password, String receivedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        Boolean matched = encoder.matches(receivedPassword,password);
        return matched;
    }
}
