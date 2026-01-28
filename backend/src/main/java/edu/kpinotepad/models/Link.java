package edu.kpinotepad.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "links")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Column(length = 256)
    private String label;

    @Column(length = 1024)
    private String fullLink;

}