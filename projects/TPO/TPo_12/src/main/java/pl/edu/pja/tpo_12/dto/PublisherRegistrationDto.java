package pl.edu.pja.tpo_12.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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

    public PublisherRegistrationDto() {}

}