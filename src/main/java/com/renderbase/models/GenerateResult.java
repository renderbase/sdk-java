package com.renderbase.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

/**
 * Result of a document generation request.
 */
public class GenerateResult {

    @JsonProperty("jobId")
    private String jobId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("downloadUrl")
    private String downloadUrl;

    @JsonProperty("expiresAt")
    private Instant expiresAt;

    @JsonProperty("format")
    private String format;

    @JsonProperty("templateId")
    private String templateId;

    public GenerateResult() {
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    @Override
    public String toString() {
        return "GenerateResult{" +
                "jobId='" + jobId + '\'' +
                ", status='" + status + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", format='" + format + '\'' +
                '}';
    }
}
