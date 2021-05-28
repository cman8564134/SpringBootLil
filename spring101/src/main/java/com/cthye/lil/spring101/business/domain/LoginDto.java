package com.cthye.lil.spring101.business.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class LoginDto {
    @NotBlank(message = "{validation.message.username}")
    @NotNull(message = "{validation.message.username}")
    private String username;

    @NotNull
    private String password;

    private String firstName;

    private String lastName;

    /**
     * Default constructor
     */
    protected LoginDto() {
    }

    /**
     * Partial constructor
     * @param username username sent by caller
     * @param password password sent by caller
     */
    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
    /**
     * Full constructor
     * @param username username sent by caller
     * @param password password sent by caller
     */
    public LoginDto(String username, String password, String firstName, String lastName) {
        this(username, password);
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
