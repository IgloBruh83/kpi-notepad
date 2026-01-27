package edu.kpinotepad.dto;

import java.util.List;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MultiDayRenderDTO {
        List<DayRenderDTO> days;
}