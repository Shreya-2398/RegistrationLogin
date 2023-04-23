package com.project.RegistrationLogin.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name="customerInfo")
public class Customer implements UserDetails {

    @Id
    @Column(name ="customer_id", length = 45)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int customerid;

    @Column(nullable = false,name="customer_name", length = 255)
    private String customername;

    @Column(name="email", length = 255)
    private String email;

    @Column(nullable = false,name="password", length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Customer(int customerid, String customername, String email, String password) {
        this.customerid = customerid;
        this.customername = customername;
        this.email = email;
        this.password = password;
    }

    public Customer() {
    }

    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {

        return this.password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerid=" + customerid +
                ", customername='" + customername + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }



    }

