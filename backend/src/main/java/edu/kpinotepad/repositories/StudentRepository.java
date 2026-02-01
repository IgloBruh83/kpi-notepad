package edu.kpinotepad.repositories;

import edu.kpinotepad.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByLogin(String login);
    List<Student> findAllByGroupIdOrderByStudentNumberAsc(Long groupId);
    @Modifying
    @Transactional
    @Query("UPDATE Student u SET u.lastLogin = :now " +
            "WHERE u.login = :login AND (u.lastLogin < :threshold OR u.lastLogin IS NULL)")
    void updateLastActivity(String login, LocalDateTime now, LocalDateTime threshold);
}
