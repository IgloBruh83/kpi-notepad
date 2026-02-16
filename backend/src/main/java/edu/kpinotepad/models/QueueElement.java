package edu.kpinotepad.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;

import java.time.LocalDateTime;

@Entity
@Table(name = "queue_elements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueueElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(length = 64)
    private String task;

    private LocalDateTime datetime;

    private Integer priority;

    private Boolean isCompleted;

    private Boolean isArchived;

}