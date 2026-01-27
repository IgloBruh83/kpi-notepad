package edu.kpinotepad.controllers;

import edu.kpinotepad.dto.MultiStudentInfoDTO;
import edu.kpinotepad.dto.StudentInfoDTO;
import edu.kpinotepad.services.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:5173")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/profile/{login}")
    public ResponseEntity<StudentInfoDTO> getProfile(@PathVariable String login) {
        return ResponseEntity.ok(studentService.getStudentProfile(login));
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<MultiStudentInfoDTO> getGroupList(@PathVariable Long groupId) {
        return ResponseEntity.ok(studentService.getGroupWithStudents(groupId));
    }
}