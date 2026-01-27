package edu.kpinotepad.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DayRenderDTO {
        LocalDate date;
        List<LessonDTO> lessons;
}