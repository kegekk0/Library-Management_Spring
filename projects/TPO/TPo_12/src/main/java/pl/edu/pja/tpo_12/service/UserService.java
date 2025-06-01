package pl.edu.pja.tpo_12.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.edu.pja.tpo_12.dto.PublisherRegistrationDto;
import pl.edu.pja.tpo_12.dto.ReaderRegistrationDto;
import pl.edu.pja.tpo_12.model.User;

public interface UserService extends UserDetailsService {
    User registerReader(ReaderRegistrationDto registrationDto);
    User registerPublisher(PublisherRegistrationDto registrationDto);
    boolean usernameExists(String username);
    boolean emailExists(String email);
}