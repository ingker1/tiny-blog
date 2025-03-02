package com.ingker.blogvue.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"archiveId", "taxonomy", "name"})
public class Archive {
    private Integer archiveId;

    @JsonProperty("name")
    private String archiveName;

    private String taxonomy;

    public Integer getArchiveId() {
        return archiveId;
    }

    public String getArchiveName() {
        return archiveName;
    }

    public String getTaxonomy() {
        return taxonomy;
    }

    public void setArchiveId(Integer archiveId) {
        this.archiveId = archiveId;
    }

    public void setArchiveName(String archiveName) {
        this.archiveName = archiveName;
    }

    public void setTaxonomy(String taxonomy) {
        this.taxonomy = taxonomy;
    }

    @Override
    public String toString() {
        return "Archive{" +
                "archiveId=" + archiveId +
                ", archiveName='" + archiveName + '\'' +
                ", taxonomy='" + taxonomy + '\'' +
                '}';
    }
}
