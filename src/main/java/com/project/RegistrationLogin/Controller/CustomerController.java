package com.project.RegistrationLogin.Controller;

import com.project.RegistrationLogin.Dto.CustomerDto;
import com.project.RegistrationLogin.Dto.LoginDto;
import com.project.RegistrationLogin.Service.CustomerService;
import com.project.RegistrationLogin.Service.implementation.AuthenticationServiceImpl;
import com.project.RegistrationLogin.response.LoginResponse;
import com.project.RegistrationLogin.response.RegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("api/v1/customer")
public class CustomerController {

    @Autowired
    private AuthenticationServiceImpl authenticationService;

    @PostMapping(path = "/register")
    public ResponseEntity<?> saveCustomer(@RequestBody CustomerDto customerDto) {
        try {
            RegisterResponse registerResponse = authenticationService.register(customerDto);
            return ResponseEntity.ok(registerResponse);
        } catch (Exception e) {
            return new ResponseEntity<>(
                "Error",
                HttpStatus.BAD_REQUEST);
        }
        ;
    }

    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> LoginCustomer(@RequestBody LoginDto loginDto) {
        try {
            LoginResponse loginResponse = authenticationService.login(loginDto);
            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "Error",
                    HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request, response);
    }
}
