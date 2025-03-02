package com.ingker.blogvue.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class CommentListDTO {
    private Integer commentId;
    private String content;
    private String author;
    private Date publishDate;
    private Integer anonymousStatus;
    private Integer parentId;
    private Integer articleId;
    private String status;
    private String articleTitle;
}
