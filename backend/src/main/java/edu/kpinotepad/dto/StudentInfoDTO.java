package edu.kpinotepad.dto;

import lombok.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentInfoDTO {
    private Long id;
    private String login;
    private String firstName;
    private String lastName;
    private String middleName;
    private Short studentNumber;
    private String avatarUrl;
    private String groupName;
    private Long groupId;
}