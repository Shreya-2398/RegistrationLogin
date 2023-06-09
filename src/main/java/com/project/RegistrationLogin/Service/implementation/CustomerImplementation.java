package com.project.RegistrationLogin.Service.implementation;

import com.project.RegistrationLogin.Entity.Role;
import com.project.RegistrationLogin.Service.JwtService;
import com.project.RegistrationLogin.response.RegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.RegistrationLogin.Dto.CustomerDto;
import com.project.RegistrationLogin.Dto.LoginDto;
import com.project.RegistrationLogin.Entity.Customer;
import com.project.RegistrationLogin.Repo.CustomerRepo;
import com.project.RegistrationLogin.Service.CustomerService;
import com.project.RegistrationLogin.response.LoginResponse;

@Service
@RequiredArgsConstructor
public class CustomerImplementation implements CustomerService {

   @Autowired
   private CustomerRepo customerRepo;

   @Autowired
   private PasswordEncoder passwordEncoder;

   @Autowired
   private PasswordHashingServiceImpl passwordHashing;

   private

   @Autowired
   private final JwtService jwtService;

   @Override
   public RegisterResponse addCustomer(CustomerDto customerDto) {
       Customer c = customerRepo.findByEmail(customerDto.getEmail());
       if (c!= null) {
           return new RegisterResponse("User exist", false,"");
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
       return new RegisterResponse("Successfully registered",true,"");

   }


//    @Override
//    public RegisterResponse addCustomer(CustomerDto customerDto){
//        var customer = Customer.builder()
//                .customername(customerDto.getCustomername())
//                .email(customerDto.getEmail())
//                .password(passwordHashing.hashPassword(customerDto.getPassword()))
//                .role(Role.USER)
//                .build();
//        var savedUser =  customerRepo.save(customer);
//        var jwtToken = jwtService.generateToken(customer);
//        var refreshToken = jwtService.generateRefreshToken(customer);
//        savedUserToken(savedUser, jwtToken);
//        return RegisterResponse.builder()
//                .token(jwtToken)
//                .refreshToken(refreshToken)
//                .build();

//    }

   @Override
   public LoginResponse loginCustomer(LoginDto loginDTO) {
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
}