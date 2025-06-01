package pl.edu.pja.tpo_12.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pja.tpo_12.dto.PublisherRegistrationDto;
import pl.edu.pja.tpo_12.dto.ReaderRegistrationDto;
import pl.edu.pja.tpo_12.model.Role;
import pl.edu.pja.tpo_12.model.User;
import pl.edu.pja.tpo_12.repository.RoleRepository;
import pl.edu.pja.tpo_12.repository.UserRepository;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerReader(ReaderRegistrationDto registrationDto) {
        if (usernameExists(registrationDto.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (emailExists(registrationDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setFirstName(registrationDto.getFirstName());
        user.setLastName(registrationDto.getLastName());
        user.setHashingAlgorithm("bcrypt");

        Role readerRole = roleRepository.findByName("ROLE_READER")
                .orElseThrow(() -> new RuntimeException("Reader role not found"));
        user.setRoles(Collections.singleton(readerRole));

        return userRepository.save(user);
    }

    @Override
    public User registerPublisher(PublisherRegistrationDto registrationDto) {
        if (usernameExists(registrationDto.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (emailExists(registrationDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setFirstName(registrationDto.getFirstName());
        user.setLastName(registrationDto.getLastName());
        user.setHashingAlgorithm("bcrypt");

        Role publisherRole = roleRepository.findByName("ROLE_PUBLISHER")
                .orElseThrow(() -> new RuntimeException("Publisher role not found"));
        user.setRoles(Collections.singleton(publisherRole));

        return userRepository.save(user);
    }

    @Override
    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}