package com.project.RegistrationLogin.Service.implementation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.RegistrationLogin.Auth.AuthenticationResponse;
import com.project.RegistrationLogin.Dto.CustomerDto;
import com.project.RegistrationLogin.Dto.LoginDto;
import com.project.RegistrationLogin.Entity.Customer;
import com.project.RegistrationLogin.Repo.CustomerRepo;
import com.project.RegistrationLogin.Service.AuthenticationService;
import com.project.RegistrationLogin.Service.JwtService;
import com.project.RegistrationLogin.response.LoginResponse;
import com.project.RegistrationLogin.response.RegisterResponse;

public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private PasswordHashingServiceImpl passwordHashing;

    @Autowired
    private JwtService jwtService;

    @Override
    public RegisterResponse register(CustomerDto customerDto) {
        Customer findCustomer = customerRepo.findByEmail(customerDto.getEmail());
        if (findCustomer != null) {
            return new RegisterResponse("User exist", false, "");
        }
        Customer customer = new Customer();
        customerDto.getCustomerid();
        customerDto.getCustomername();
        customerDto.getEmail();

        customer.setCustomerid(customerDto.getCustomerid());
        customer.setCustomername(customerDto.getCustomername());
        customer.setEmail(customerDto.getEmail());

        String hashedPass = passwordHashing.hashPassword(customerDto.getPassword());
        customer.setPassword(hashedPass);

        customerRepo.save(customer);
        return new RegisterResponse("Successfully registered", true, "");
    }

    @Override
    public AuthenticationResponse login(LoginDto loginDto) {
        Customer customer = customerRepo.findByEmail(loginDTO.getEmail());
        if (customer == null) {
            return new LoginResponse("User does not exist", false);
        }
        Boolean matched = passwordHashing.verifyPassword(customer.getPassword(), loginDTO.getPassword());
        if (!matched) {
            return new LoginResponse("Password Not Match", false);
        }

        // Issue Access Token
        return new LoginResponse("Login Successful", true);
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'refreshToken'");
    }

}
