package edu.kpinotepad.models;

import edu.kpinotepad.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "students", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"group_id", "local_number"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    // --- UID ---

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Account data ---

    @Column(nullable = false, unique = true, length = 100)
    private String login;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role = UserRole.ROLE_STUDENT;

    private boolean isEnabled = true;

    // --- Personal user data ---

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "middle_name", length = 50)
    private String middleName;

    @Column(name = "local_number")
    private Short studentNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private UniGroup group;

    // --- Metadata ---

    private String avatarUrl;

    @Column(length = 100)
    private String telegramUsername;

    @Column(length = 100)
    private String email;

    @Column(length = 30)
    private String phone;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
