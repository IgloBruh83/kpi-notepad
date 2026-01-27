package edu.kpinotepad.models;

import edu.kpinotepad.enums.LessonStatus;
import edu.kpinotepad.enums.LessonType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lessons")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    private ScheduleItem template;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private UniGroup group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    private LocalDateTime dateTime;

    private Short position;

    @Enumerated(EnumType.STRING)
    private LessonType type;

    @Column(length = 64)
    private String place;

    @Column(length = 512)
    private String link;

    @Enumerated(EnumType.STRING)
    private LessonStatus status;

    @Column(columnDefinition = "TEXT")
    private String notes;
}