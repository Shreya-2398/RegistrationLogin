package com.project.RegistrationLogin.Controller;


import com.project.RegistrationLogin.Dto.CustomerDto;
import com.project.RegistrationLogin.Dto.LoginDto;
import com.project.RegistrationLogin.Service.CustomerService;
import com.project.RegistrationLogin.response.LoginResponse;
import com.project.RegistrationLogin.response.RegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("api/v1/customer")
public class CustomerController  {

    @Autowired
    private CustomerService customerService;

    @PostMapping(path = "/register")
    public ResponseEntity<?> saveCustomer(@RequestBody CustomerDto customerDto){
        RegisterResponse registerResponse = customerService.addCustomer(customerDto);
       return ResponseEntity.ok(registerResponse);

    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> LoginCustomer(@RequestBody LoginDto loginDto){
        LoginResponse loginResponse = customerService.loginCustomer(loginDto);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        customerService.refreshToken(request, response);
    }
}
