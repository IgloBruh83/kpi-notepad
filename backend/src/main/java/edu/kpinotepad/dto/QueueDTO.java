package edu.kpinotepad.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class QueueDTO {
    private Long id;
    private Long subjectId;
    private String studentFullName;
    private String studentLogin;
    private String task;
    private LocalDateTime datetime;
    private Integer priority;
    private Boolean isCompleted;
}