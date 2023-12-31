package com.loloao.vo;

import com.loloao.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleVo extends Article {

    private Integer year;

    private Integer month;

    private Long tagId;

    private Long categoryId;

    private Integer count;

    private String keyword;

    private Long starUid;

}