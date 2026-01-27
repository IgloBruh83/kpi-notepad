package edu.kpinotepad.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "uni_groups")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UniGroup {

    // --- UID ---

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- University group data ---

    @Column(nullable = false, unique = true, length = 15)
    private String name;

}
