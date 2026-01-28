package edu.kpinotepad.repositories;

import edu.kpinotepad.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findAllByOrderByGeneralDescNameAsc();
}