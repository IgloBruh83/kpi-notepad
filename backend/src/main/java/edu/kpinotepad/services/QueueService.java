package edu.kpinotepad.services;

import edu.kpinotepad.models.*;
import edu.kpinotepad.repositories.*;
import edu.kpinotepad.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QueueService {
    private final QueueElementRepository queueRepository;
    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;

    public List<QueueDTO> getActiveQueueBySubject(Long subjectId) {
        return queueRepository.findBySubjectIdAndIsArchivedFalseOrderByPriorityDescDatetimeAsc(subjectId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public MultiSubjectQueueDTO getAllGeneralQueues() {
        List<Subject> compulsorySubjects = subjectRepository.findByGeneralTrue();
        List<SubjectQueueDTO> resultList = new ArrayList<>();

        for (Subject subject : compulsorySubjects) {
            List<QueueDTO> queue = queueRepository
                    .findBySubjectIdAndIsArchivedFalseOrderByPriorityDescDatetimeAsc(subject.getId())
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            resultList.add(new SubjectQueueDTO(
                    subject.getId(),
                    subject.getName(),
                    queue
            ));
        }

        return new MultiSubjectQueueDTO(resultList);
    }

    @Transactional
    public QueueDTO createEntry(QueueCreateRequest request) {
        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));
        Student student = studentRepository.findByLogin(request.getStudentLogin())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        QueueElement element = QueueElement.builder()
                .subject(subject)
                .student(student)
                .task(request.getTask())
                .priority(request.getPriority() != null ? request.getPriority() : 1)
                .datetime(LocalDateTime.now())
                .isCompleted(false)
                .isArchived(false)
                .build();

        return convertToDTO(queueRepository.save(element));
    }

    @Transactional
    public void markAsCompleted(Long id) {
        QueueElement element = queueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Element not found"));
        element.setIsCompleted(true);
        queueRepository.save(element);
    }

    @Transactional
    public void deleteElement(Long id) {
        if (!queueRepository.existsById(id)) {
            throw new RuntimeException("Запис не знайдено");
        }
        queueRepository.deleteById(id);
    }

    private QueueDTO convertToDTO(QueueElement entity) {
        QueueDTO dto = new QueueDTO();
        dto.setId(entity.getId());
        dto.setSubjectId(entity.getSubject().getId());
        dto.setStudentFullName(entity.getStudent().getLastName() + " " + entity.getStudent().getFirstName());
        dto.setStudentLogin(entity.getStudent().getLogin());
        dto.setTask(entity.getTask());
        dto.setDatetime(entity.getDatetime());
        dto.setPriority(entity.getPriority());
        dto.setIsCompleted(entity.getIsCompleted());
        return dto;
    }
}