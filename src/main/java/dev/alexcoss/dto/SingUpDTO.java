package dev.alexcoss.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class SingUpDTO {
    @NotEmpty(message = "Username should not be empty")
    @Size(min = 2, max = 100, message = "Username should be between 2 and 100 characters")
    @Column(name = "username")
    private String username;

    @Min(value = 1900)
    @Column(name = "year_of_birth")
    private int yearOfBirth;

    @NotEmpty(message = "Password should not be empty")
    @Size(max = 250, message = "Password should be maximum 250 characters")
    @Column(name = "password")
    private String password;

    public SingUpDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
