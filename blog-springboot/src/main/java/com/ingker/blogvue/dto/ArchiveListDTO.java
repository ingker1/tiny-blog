package com.ingker.blogvue.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonPropertyOrder({"archiveId", "taxonomy", "name", "count"})
public class ArchiveListDTO {
    private Integer archiveId;

    @JsonProperty("name")
    private String archiveName;

    private String taxonomy;
    private Integer count;

    public ArchiveListDTO(Integer archiveId, String archiveName, String taxonomy, Integer count) {
        this.archiveId = archiveId;
        this.archiveName = archiveName;
        this.taxonomy = taxonomy;
        this.count = count;
    }
}
