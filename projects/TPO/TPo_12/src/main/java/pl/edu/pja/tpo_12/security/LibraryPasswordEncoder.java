package pl.edu.pja.tpo_12.security;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.Map;

public class LibraryPasswordEncoder implements PasswordEncoder {

    private final Map<String, PasswordEncoder> encoders = Map.of(
            "bcrypt", new BCryptPasswordEncoder(),
            "scrypt", new SCryptPasswordEncoder(),
            "pbkdf2", new Pbkdf2PasswordEncoder(),
            "argon2", new Argon2PasswordEncoder()
    );

    @Override
    public String encode(CharSequence rawPassword) {
        return encoders.get("bcrypt").encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String algorithm = extractAlgorithm(encodedPassword);
        return encoders.get(algorithm).matches(rawPassword, encodedPassword);
    }

    private String extractAlgorithm(String encodedPassword) {

    }
}