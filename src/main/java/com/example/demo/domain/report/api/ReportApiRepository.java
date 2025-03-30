package com.example.demo.domain.report.api;

import com.example.demo.domain.report.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ReportApiRepository extends JpaRepository<Report, Long> {

    int countByTypeAndCreatedAtAfter(String type, LocalDateTime time);
}
