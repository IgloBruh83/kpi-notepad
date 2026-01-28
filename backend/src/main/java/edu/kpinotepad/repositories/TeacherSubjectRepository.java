package edu.kpinotepad.repositories;

import edu.kpinotepad.models_asoc.TeacherSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherSubjectRepository extends JpaRepository<TeacherSubject, Long> {
    List<TeacherSubject> findAllBySubjectIdIn(List<Long> subjectIds);
}