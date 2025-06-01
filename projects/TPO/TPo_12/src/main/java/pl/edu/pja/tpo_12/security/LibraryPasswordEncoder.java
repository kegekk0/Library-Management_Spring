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
            "scrypt", SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8(),
            "pbkdf2", Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8(),
            "argon2", Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8()
    );

    @Override
    public String encode(CharSequence rawPassword) {
        return encoders.get("bcrypt").encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String algorithm = extractAlgorithm(encodedPassword);
        PasswordEncoder encoder = encoders.get(algorithm);
        if (encoder == null) {
            encoder = encoders.get("bcrypt");
        }
        return encoder.matches(rawPassword, encodedPassword);
    }

    private String extractAlgorithm(String encodedPassword) {
        if (encodedPassword == null || encodedPassword.isEmpty()) {
            return "bcrypt";
        }

        if (encodedPassword.startsWith("$2a$") || encodedPassword.startsWith("$2b$") ||
                encodedPassword.startsWith("$2x$") || encodedPassword.startsWith("$2y$")) {
            return "bcrypt";
        }

        if (encodedPassword.startsWith("$s0$") || encodedPassword.startsWith("$s1$")) {
            return "scrypt";
        }

        if (encodedPassword.startsWith("$argon2")) {
            return "argon2";
        }

        if (encodedPassword.contains("$pbkdf2$")) {
            return "pbkdf2";
        }

        return "bcrypt";
    }
}