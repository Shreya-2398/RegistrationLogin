package com.project.RegistrationLogin.Service.implementation;

import com.project.RegistrationLogin.Dto.CustomerDto;
import com.project.RegistrationLogin.Dto.LoginDto;
import com.project.RegistrationLogin.Entity.Customer;
import com.project.RegistrationLogin.Repo.CustomerRepo;
import com.project.RegistrationLogin.Service.CustomerService;
import com.project.RegistrationLogin.response.LoginMessage;
import com.project.RegistrationLogin.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerImplementation implements CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordHashingServiceImplementation passwordHashing;


    @Override
    public Customer registerNewUser(Customer customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return customerRepo.save(customer);
    }

    @Override
    public void validateUserCredentials(String email,ng password) throws InvalidCredentialsException {
        Customer customer = customerRepo.findByEmail();
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }
    }

    @Override
    public String addCustomer(CustomerDto customerDto) {
        return null;
    }

    @Override
    public LoginResponse loginCustomer(LoginDto loginDto) {
        return null;
    }
}
//    @Override
//    public String addCustomer(CustomerDto customerDto) {
//
//        Customer customer = new Customer();
//        customerDto.getCustomerid();
//        customerDto.getCustomername();
//        customerDto.getEmail();
////        String encodedPassword = bCryptPasswordEncoder
////                .encode(customerDto.getPassword());
////
////        customerDto.setPassword(encodedPassword);
//
//
//        this.passwordEncoder.encode(customerDto.getPassword());
//
//        customerRepo.save(customer);
//        return customer.getCustomername();
//
//    }
//    CustomerDto customerDto;
//
//    @Override
//    public LoginResponse  loginCustomer(LoginDto loginDTO) {
//        String msg = "";
//        Customer customer1 = customerRepo.findByEmail(loginDTO.getEmail());
//        if (customer1 != null) {
//            String password = loginDTO.getPassword();
//            String encodedPassword = customer1.getPassword();
//            Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
//            if (isPwdRight) {
//                Optional<Customer> customer = customerRepo.findOneByEmailAndPassword(loginDTO.getEmail(), encodedPassword);
//                if (customer.isPresent()) {
//                    return new LoginResponse("Login Success", true);
//                } else {
//                    return new LoginResponse("Login Failed", false);
//                }
//            } else {
//
//                return new LoginResponse("password Not Match", false);
//            }
//        }else {
//            return new LoginResponse("Email not exits", false);
//        }
//
//
//    }
//
//}