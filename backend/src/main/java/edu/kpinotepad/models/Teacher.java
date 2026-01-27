package edu.kpinotepad.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "teachers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Full name ---

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "middle_name", length = 100)
    private String middleName;

    // --- Short name ---

    @Column(name = "display_name", length = 50)
    private String displayName;

    // --- Other data and contacts ---

    @Column(name = "department")
    private String department;

    @Column(length = 100)
    private String email;

    @Column(length = 100)
    private String telegramUsername;

    @Column(length = 30)
    private String phone;


    @Column(name = "avatar_url")
    private String avatarUrl;

    // --- Full teacher description ---

    @Column(columnDefinition = "TEXT")
    private String description;
}