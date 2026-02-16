package edu.kpinotepad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class SubjectQueueDTO {
    private Long subjectId;
    private String subjectName;
    private List<QueueDTO> queue;
}