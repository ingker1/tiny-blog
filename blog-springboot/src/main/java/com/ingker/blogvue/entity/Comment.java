package com.ingker.blogvue.entity;

import java.util.Date;

public class Comment {
    private Integer commentId;
    private String content;
    private String author;
    private Date publishDate;
    private Integer anonymousStatus;
    private Integer parentId;
    private Integer articleId;
    private String status;

    public void setAnonymousStatus(Integer anonymousStatus) {
        this.anonymousStatus = anonymousStatus;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public Integer getAnonymousStatus() {
        return anonymousStatus;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", publishDate=" + publishDate +
                ", anonymousStatus=" + anonymousStatus +
                ", parentId=" + parentId +
                ", articleId=" + articleId +
                ", status=" + status +
                '}';
    }
}
