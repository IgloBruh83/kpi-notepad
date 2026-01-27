package edu.kpinotepad.services;

import edu.kpinotepad.dto.MultiStudentInfoDTO;
import edu.kpinotepad.dto.StudentInfoDTO;
import edu.kpinotepad.models.Student;
import edu.kpinotepad.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Отримати одного студента за логіном
    public StudentInfoDTO getStudentProfile(String login) {
        return studentRepository.findByLogin(login)
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    // Отримати всіх студентів групи
    public MultiStudentInfoDTO getGroupWithStudents(Long groupId) {
        List<Student> students = studentRepository.findAllByGroupIdOrderByStudentNumberAsc(groupId);

        if (students.isEmpty()) {
            return new MultiStudentInfoDTO("Unknown Group", List.of());
        }

        String groupName = students.getFirst().getGroup().getName();
        List<StudentInfoDTO> studentDtos = students.stream()
                .map(this::convertToDto)
                .toList();

        return new MultiStudentInfoDTO(groupName, studentDtos);
    }

    private StudentInfoDTO convertToDto(Student student) {
        return StudentInfoDTO.builder()
                .id(student.getId())
                .login(student.getLogin())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .middleName(student.getMiddleName())
                .studentNumber(student.getStudentNumber())
                .avatarUrl(student.getAvatarUrl())
                .groupName(student.getGroup() != null ? student.getGroup().getName() : null)
                .groupId(student.getGroup() != null ? student.getGroup().getId() : null)
                .build();
    }
}