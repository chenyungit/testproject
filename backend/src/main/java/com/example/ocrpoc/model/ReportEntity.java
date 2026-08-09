package com.example.ocrpoc.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reports")
public class ReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "report_uuid", nullable = false, unique = true)
    private String reportUuid;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "source")
    private String source;

    @Column(name = "original_file_url", columnDefinition = "TEXT")
    private String originalFileUrl;

    @Column(name = "text_extract", columnDefinition = "LONGTEXT")
    private String textExtract;

    @Column(name = "status")
    private String status;

    @Column(name = "red_flag")
    private Boolean redFlag;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParsedItemEntity> parsedItems = new ArrayList<>();

    public ReportEntity() {}

    public ReportEntity(String reportUuid) {
        this.reportUuid = reportUuid;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.status = "queued";
        this.redFlag = false;
    }

    // getters and setters
    public Long getId() { return id; }
    public String getReportUuid() { return reportUuid; }
    public void setReportUuid(String reportUuid) { this.reportUuid = reportUuid; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public String getOriginalFileUrl() { return originalFileUrl; }
    public void setOriginalFileUrl(String originalFileUrl) { this.originalFileUrl = originalFileUrl; }
    public String getTextExtract() { return textExtract; }
    public void setTextExtract(String textExtract) { this.textExtract = textExtract; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Boolean getRedFlag() { return redFlag; }
    public void setRedFlag(Boolean redFlag) { this.redFlag = redFlag; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
    public List<ParsedItemEntity> getParsedItems() { return parsedItems; }
    public void setParsedItems(List<ParsedItemEntity> parsedItems) { this.parsedItems = parsedItems; }
    public void addParsedItem(ParsedItemEntity item) {
        parsedItems.add(item);
        item.setReport(this);
    }
}
