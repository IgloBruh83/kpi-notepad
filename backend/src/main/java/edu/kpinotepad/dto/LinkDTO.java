package edu.kpinotepad.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LinkDTO {
    private String label;
    private String url;
}