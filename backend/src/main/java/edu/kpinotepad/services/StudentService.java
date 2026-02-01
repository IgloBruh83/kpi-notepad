package edu.kpinotepad.services;

import edu.kpinotepad.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    @Transactional
    public void updateLastLogin(String username) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime threshold = now.minusMinutes(5);
        studentRepository.updateLastActivity(username, now, threshold);
    }
}