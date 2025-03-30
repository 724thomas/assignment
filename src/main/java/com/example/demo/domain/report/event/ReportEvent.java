package com.example.demo.domain.report.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportEvent {
    private String type;
    private Long relativeId;

    public static ReportEvent of(String type, Long relativeId) {
        return ReportEvent.builder()
                .type(type)
                .relativeId(relativeId)
                .build();
    }
}
