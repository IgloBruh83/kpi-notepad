package edu.kpinotepad.dto;

import java.time.LocalDateTime;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LessonDTO {
    private Long id;
    private String subjectName;
    private String teacherName;
    private String group;
    private String place;
    private Short position;
    private LocalDateTime dateTime;
    private String type;
    private String link;
}