package com.ingker.blogvue.entity;

public class ArchiveRelationship {
    private Integer archiveRelationshipId;
    private Integer archiveId;
    private Integer articleId;

    public Integer getArchiveId() {
        return archiveId;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public Integer getArchiveRelationshipId() {
        return archiveRelationshipId;
    }

    @Override
    public String toString() {
        return "ArchiveRelationship{" +
                "archiveRelationshipId=" + archiveRelationshipId +
                ", archiveId=" + archiveId +
                ", articleId=" + articleId +
                '}';
    }
}
