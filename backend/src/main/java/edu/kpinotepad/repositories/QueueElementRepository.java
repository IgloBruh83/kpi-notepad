package edu.kpinotepad.repositories;

import edu.kpinotepad.models.QueueElement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QueueElementRepository extends JpaRepository<QueueElement, Long> {
    List<QueueElement> findBySubjectIdAndIsArchivedFalseOrderByPriorityDescDatetimeAsc(Long subjectId);
}