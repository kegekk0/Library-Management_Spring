package pl.edu.pja.tpo_12.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PublisherRegistrationDto {

    @NotEmpty
    @Size(min = 4, max = 20)
    private String username;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    @Size(min = 6)
    private String password;

    @NotEmpty
    private String confirmPassword;

    @NotEmpty
    private String position;

    private Long publisherId;

    // Getters and setters
}