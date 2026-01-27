package edu.kpinotepad.repositories;

import edu.kpinotepad.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findAllByDateTimeBetweenOrderByDateTimeAsc(LocalDateTime start, LocalDateTime end);

    @Query("SELECT l FROM Lesson l JOIN l.subject s " +
            "WHERE l.dateTime BETWEEN :start AND :end " +
            "AND (s.general = true OR l.subject.id IN " +
            "(SELECT ss.subject.id FROM StudentSubject ss WHERE ss.student.login = :login)) " +
            "ORDER BY l.dateTime ASC")
    List<Lesson> findFilteredSchedule(
            @Param("login") String login,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}