package edu.kpinotepad.security;

import edu.kpinotepad.models.Student;
import edu.kpinotepad.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final StudentRepository studentRepository;

    @Override
    @Transactional
    @NullMarked
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Student student = studentRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Користувача з логіном " + login + " не знайдено"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(student.getLogin())
                .password(student.getPassword())
                .authorities(student.getRole().name().replace("ROLE_", ""))
                .build();
    }
}