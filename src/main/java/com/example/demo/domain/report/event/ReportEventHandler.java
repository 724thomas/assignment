package com.example.demo.domain.report.event;

import com.example.demo.domain.report.Report;
import com.example.demo.domain.report.api.ReportApiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportEventHandler {

    private final ReportApiRepository reportApiRepository;

    @EventListener
    @Async
    public void handleReportEvent(ReportEvent reportEvent) {
        reportApiRepository.save(
                Report.builder()
                        .type(reportEvent.getType())
                        .relativeId(reportEvent.getRelativeId())
                        .build());
    }
}
