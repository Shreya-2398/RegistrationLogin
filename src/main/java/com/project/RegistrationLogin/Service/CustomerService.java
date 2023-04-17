package com.project.RegistrationLogin.Service;

import com.project.RegistrationLogin.Dto.CustomerDto;
import com.project.RegistrationLogin.Dto.LoginDto;
import com.project.RegistrationLogin.response.LoginMessage;
import com.project.RegistrationLogin.response.LoginResponse;

public interface CustomerService {
    String addCustomer(CustomerDto customerDto);

    LoginResponse loginCustomer(LoginDto loginDto);
}
