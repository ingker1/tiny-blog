package com.ingker.blogvue.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ArticleCommentCount {
    private Integer articleId;
    private Integer count;
}
