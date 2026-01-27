package edu.kpinotepad.models_asoc;

import edu.kpinotepad.models.Subject;
import edu.kpinotepad.models.Teacher;
import edu.kpinotepad.enums.TeacherSubjectRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "teacher_subjects")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    @ToString.Exclude
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    @ToString.Exclude
    private Teacher teacher;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TeacherSubjectRole role;
}