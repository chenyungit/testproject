package com.example.ocrpoc.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "parsed_items")
public class ParsedItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id")
    private ReportEntity report;

    @Column(name = "raw_name")
    private String rawName;

    @Column(name = "standard_name")
    private String standardName;

    @Column(name = "value")
    private String value;

    @Column(name = "unit")
    private String unit;

    @Column(name = "ref_range")
    private String refRange;

    @Column(name = "abnormal_level")
    private String abnormalLevel;

    @Column(name = "confidence")
    private Double confidence;

    @Column(name = "created_at")
    private Instant createdAt;

    public ParsedItemEntity() {}

    @PrePersist
    public void prePersist() { this.createdAt = Instant.now(); }

    // getters and setters
    public Long getId() { return id; }
    public ReportEntity getReport() { return report; }
    public void setReport(ReportEntity report) { this.report = report; }
    public String getRawName() { return rawName; }
    public void setRawName(String rawName) { this.rawName = rawName; }
    public String getStandardName() { return standardName; }
    public void setStandardName(String standardName) { this.standardName = standardName; }
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    public String getRefRange() { return refRange; }
    public void setRefRange(String refRange) { this.refRange = refRange; }
    public String getAbnormalLevel() { return abnormalLevel; }
    public void setAbnormalLevel(String abnormalLevel) { this.abnormalLevel = abnormalLevel; }
    public Double getConfidence() { return confidence; }
    public void setConfidence(Double confidence) { this.confidence = confidence; }
    public Instant getCreatedAt() { return createdAt; }
}
