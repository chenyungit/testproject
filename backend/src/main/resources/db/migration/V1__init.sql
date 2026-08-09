-- V1__init.sql: initialize schema for PoC

CREATE TABLE IF NOT EXISTS users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  openid VARCHAR(128) UNIQUE,
  phone VARCHAR(32),
  age INT,
  sex VARCHAR(8),
  consent BOOLEAN DEFAULT FALSE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS reports (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  report_uuid VARCHAR(64) NOT NULL,
  user_id BIGINT,
  source VARCHAR(64),
  original_file_url TEXT,
  text_extract LONGTEXT,
  status VARCHAR(32) DEFAULT 'queued',
  red_flag BOOLEAN DEFAULT FALSE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE INDEX idx_reports_report_uuid ON reports(report_uuid);

CREATE TABLE IF NOT EXISTS parsed_items (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  report_id BIGINT,
  raw_name VARCHAR(256),
  standard_name VARCHAR(256),
  value VARCHAR(128),
  unit VARCHAR(64),
  ref_range VARCHAR(64),
  abnormal_level VARCHAR(32),
  confidence DOUBLE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (report_id) REFERENCES reports(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS interpretations (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  report_id BIGINT,
  engine_version VARCHAR(128),
  content LONGTEXT,
  manual_review_by VARCHAR(128),
  manual_review_at TIMESTAMP NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (report_id) REFERENCES reports(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS templates (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(256),
  standard_name VARCHAR(256),
  template_text LONGTEXT,
  severity_rules LONGTEXT,
  created_by VARCHAR(128),
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS audit_logs (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  report_id BIGINT,
  user_id BIGINT,
  action VARCHAR(128),
  detail LONGTEXT,
  actor VARCHAR(128),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
