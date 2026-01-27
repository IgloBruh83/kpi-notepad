package edu.kpinotepad.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordControl {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String hash (String plainPassword) {
        return encoder.encode(plainPassword);
    }

    public boolean match (String plainPassword, String hashedPassword) {
        return encoder.matches(plainPassword, hashedPassword);
    }
}
