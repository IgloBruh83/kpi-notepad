package edu.kpinotepad.dto;

import lombok.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDTO {
    private Long subjectId;
    private String subjectName;
    private Boolean general;
    private List<LinkDTO> officialLinks;
    private String generalInfo;
    private String conditions;
    private List<TeacherSubjectDTO> teachers;
}
