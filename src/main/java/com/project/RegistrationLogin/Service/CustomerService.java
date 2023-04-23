package com.project.RegistrationLogin.Service;

import com.project.RegistrationLogin.Dto.CustomerDto;
import com.project.RegistrationLogin.Dto.LoginDto;
import com.project.RegistrationLogin.response.LoginResponse;
import com.project.RegistrationLogin.response.RegisterResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CustomerService {
    RegisterResponse addCustomer(CustomerDto customerDto);

    LoginResponse loginCustomer(LoginDto loginDto);

    void refreshToken(HttpServletRequest request, HttpServletResponse response);
}
