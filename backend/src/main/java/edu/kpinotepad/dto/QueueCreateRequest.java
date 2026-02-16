package edu.kpinotepad.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class QueueCreateRequest {
    private Long subjectId;
    private String studentLogin;
    private String task;
    private Integer priority;
}