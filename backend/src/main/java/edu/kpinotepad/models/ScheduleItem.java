package edu.kpinotepad.models;

import edu.kpinotepad.enums.LessonType;
import edu.kpinotepad.enums.RepeatType;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "schedule_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private UniGroup group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @Enumerated(EnumType.STRING)
    private LessonType type;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    private Short position;

    @Column(length = 64)
    private String place;

    @Column(length = 512)
    private String link;

    @Enumerated(EnumType.STRING)
    private RepeatType repeat;

    private LocalDate startDate;
    private LocalDate stopDate;
}