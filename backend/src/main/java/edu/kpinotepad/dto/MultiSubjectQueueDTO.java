package edu.kpinotepad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class MultiSubjectQueueDTO {
    private List<SubjectQueueDTO> subjects;
}