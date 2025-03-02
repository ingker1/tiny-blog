package com.ingker.blogvue.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class CommentDTO {
    private Integer commentId;
    private String content;
    private String author;
    private Date publishDate;
    private Integer anonymousStatus;
    private Integer parentId;
    private Integer articleId;
    private String status;
    private List<CommentDTO> children;
}
