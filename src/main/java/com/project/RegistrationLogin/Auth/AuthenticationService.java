package com.project.RegistrationLogin.Auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.RegistrationLogin.Dto.CustomerDto;
import com.project.RegistrationLogin.Entity.Customer;
import com.project.RegistrationLogin.Entity.Role;
import com.project.RegistrationLogin.Repo.CustomerRepo;
import com.project.RegistrationLogin.Service.JwtService;
import com.project.RegistrationLogin.Service.implementation.PasswordHashingServiceImplementation;
import com.project.RegistrationLogin.Token.Token;
import com.project.RegistrationLogin.Token.TokenRepository;
import com.project.RegistrationLogin.Token.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final CustomerRepo customerRepo;
    private final TokenRepository tokenRepository;
    private final PasswordHashingServiceImplementation passwordHashing;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(CustomerDto customerDto) {
        var customer = Customer.builder()
                .customername(customerDto.getCustomername())
                .email(customerDto.getEmail())
                .password(passwordHashing.hashPassword(customerDto.getPassword()))
                .role(Role.USER)
                .build();

        var savedcustomer =  customerRepo.save(customer);
        var jwtToken = jwtService.generateToken(customer);
        var refreshToken = jwtService.generateRefreshToken(customer);
        
        saveCustomerToken(savedcustomer, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var customer = customerRepo.findByEmail(request.getEmail())
                .orElseThrow()
        var jwtToken = jwtService.generateToken(customer);
        var refreshToken = jwtService.generateRefreshToken(customer);
        revokeAllCustomerTokens(customer);
        saveCustomerToken(customer, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
    private void saveCustomerToken(Customer customer, String jwtToken) {
        var token = Token.builder()
                .customer(customer)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }
    private void revokeAllCustomerTokens(Customer customer) {
        var validCustomerTokens = tokenRepository.findAllValidTokenByUser(customer.getCustomerid());
        if (validCustomerTokens.isEmpty())
            return;
        validCustomerTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validCustomerTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String customerEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        customerEmail = jwtService.extractCustomerName(refreshToken);
        if (customerEmail != null) {
            var customer = this.customerRepo.findByEmail(customerEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, customer)) {
                var accessToken = jwtService.generateToken(customer);
                revokeAllCustomerTokens(customer);
                saveCustomerToken(customer, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
