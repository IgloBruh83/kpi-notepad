package edu.kpinotepad.dto;

import lombok.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MultiStudentInfoDTO {
    private String groupName;
    private List<StudentInfoDTO> students;
}
