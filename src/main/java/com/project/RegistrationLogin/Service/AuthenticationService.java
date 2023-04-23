package com.project.RegistrationLogin.Service;

import com.project.RegistrationLogin.Auth.AuthenticationResponse;
import com.project.RegistrationLogin.Dto.CustomerDto;
import com.project.RegistrationLogin.Dto.LoginDto;
import com.project.RegistrationLogin.response.RegisterResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthenticationService {

    RegisterResponse register(CustomerDto customerDto);

    AuthenticationResponse login(LoginDto loginDto);

    void refreshToken(HttpServletRequest request, HttpServletResponse response);
}
