package com.cthye.lil.spring101.data.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "GUEST")
public class Guest {

    @Id
    @Column(name = "GUEST_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FIRST_NAME")
    @NotBlank(message = "Required Guest FName ")
    private String firstname;

    @Column(name = "LAST_NAME")
    @NotBlank(message = "Required Guest LName ")
    private String lastname;

    @Column(name = "EMAIL_ADDRESS")
    @NotBlank(message = "Required Guest Email ")
    @Email(message = "Invalid Email Address")
    private String email;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "STATE")
    private String state;

    @Column(name = "PHONE_NUMBER")
    private String phone_number;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String first_name) {
        this.firstname = first_name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String last_name) {
        this.lastname = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
