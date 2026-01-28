package edu.kpinotepad.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherSubjectDTO {
    private Long teacherId;
    private String teacherName;
    private String teacherAvatarUrl;
    private String teacherRole;
}
