package com.project.RegistrationLogin.Controller;


import com.project.RegistrationLogin.Dto.CustomerDto;
import com.project.RegistrationLogin.Dto.LoginDto;
import com.project.RegistrationLogin.Service.CustomerService;
import com.project.RegistrationLogin.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/v1/customer")
public class CustomerController  {

    @Autowired
    private CustomerService customerService;

    @PostMapping(path = "/save")
    public String saveCustomer(@RequestBody CustomerDto customerDto){

        String id = customerService.addCustomer(customerDto);
        return id;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> LoginCustomer(@RequestBody LoginDto loginDto){
        LoginResponse loginResponse = customerService.loginCustomer(loginDto);
        return ResponseEntity.ok(loginResponse);
    }
}
