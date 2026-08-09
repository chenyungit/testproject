package com.example.ocrpoc.repository;

import com.example.ocrpoc.model.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<ReportEntity, Long> {
    Optional<ReportEntity> findByReportUuid(String reportUuid);
}
