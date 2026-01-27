package edu.kpinotepad.repositories;

import edu.kpinotepad.models.ScheduleItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleItemRepository extends JpaRepository<ScheduleItem, Long> {
}