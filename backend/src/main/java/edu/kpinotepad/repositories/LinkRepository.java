package edu.kpinotepad.repositories;

import edu.kpinotepad.models.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LinkRepository extends JpaRepository<Link, Long> {
    List<Link> findAllBySubjectIdIn(List<Long> subjectIds);
}
