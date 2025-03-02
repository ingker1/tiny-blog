package com.ingker.blogvue.dto;

import com.ingker.blogvue.entity.Archive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ArticleArchive {
    private Integer articleId;   // 对应的文章 ID
    private Archive archive;
}
