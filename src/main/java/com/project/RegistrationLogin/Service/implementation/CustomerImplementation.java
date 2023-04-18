package com.project.RegistrationLogin.Service.implementation;

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
public class CustomerImplementation implements CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordHashingServiceImplementation passwordHashing;

    // @Override
    // public Customer registerNewUser(Customer customer) {
    //     customer.setPassword(passwordHashing.hashPassword(customer.getPassword()));
    //     return customerRepo.save(customer);
    // }

    // @Override
    // public void validateUserCredentials(String email,ng password) throws InvalidCredentialsException {
    //     Customer customer = customerRepo.findByEmail();
    //             .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

    //     if (!passwordEncoder.matches(password, user.getPassword())) {
    //         throw new InvalidCredentialsException("Invalid username or password");
    //     }
    // }

    @Override
    public String addCustomer(CustomerDto customerDto) {

        Customer customer = new Customer();
        customerDto.getCustomerid();
        customerDto.getCustomername();
        customerDto.getEmail();
        // String hashedPass = bCryptPasswordEncoder
        // .encode(customerDto.getPassword());

        String hashedPass = passwordHashing.hashPassword(customerDto.getPassword());

        customerDto.setPassword(hashedPass);

        this.passwordEncoder.encode(customerDto.getPassword());

        customerRepo.save(customer);
        return customer.getCustomername();

    }

    @Override
    public LoginResponse loginCustomer(LoginDto loginDTO) {
        Customer customer = customerRepo.findByEmail(loginDTO.getEmail());
        if (customer == null) {
            return new LoginResponse("User does not exist", false);
        }
        // Boolean matched = passwordEncoder.matches(loginDTO.getPassword(),
        // customer.getPassword());
        Boolean matched = passwordHashing.verifyPassword(customer.getPassword(), loginDTO.getPassword());
        if (!matched) {
            return new LoginResponse("Password Not Match", false);
        }

        // Issue Access Token
        return new LoginResponse("Login Failed", false);

    }
}