package edu.kpinotepad.controllers;

import edu.kpinotepad.dto.AuthRequest;
import edu.kpinotepad.dto.AuthResponse;
import edu.kpinotepad.models.Student;
import edu.kpinotepad.repositories.StudentRepository;
import edu.kpinotepad.services.PasswordControl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AuthController {

    private final StudentRepository studentRepository;
    private final PasswordControl passwordControl;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request, HttpServletResponse response) {
        return studentRepository.findByLogin(request.getLogin())
                .map(student -> {
                    if (passwordControl.match(request.getPassword(), student.getPassword())) {

                        Cookie authCookie = new Cookie("authToken", student.getLogin());
                        authCookie.setHttpOnly(true);
                        authCookie.setPath("/");
                        authCookie.setMaxAge(604800);
                        response.addCookie(authCookie);

                        return ResponseEntity.ok(AuthResponse.builder()
                                .success(true)
                                .message("SUCCESS: Auth")
                                .role(student.getRole().name())
                                .username(student.getFirstName())
                                .build());
                    }
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(new AuthResponse(false, "FAIL: Invalid password", null, null));
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new AuthResponse(false, "FAIL: Invalid login", null, null)));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("authToken", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);

        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }
}