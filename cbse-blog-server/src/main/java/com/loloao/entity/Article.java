package com.loloao.entity;

import java.util.Date;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Article)表实体类
 *
 * @author makejava
 * @since 2023-12-09 18:06:12
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("me_article")
public class Article  {



    public static final int Article_TOP = 1;

    public static final int Article_Common = 0;


    //primary key
    @TableId
    private Long id;

    
    private Integer commentCounts;

    //@JSONField(format = "yyyy.MM.dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createDate;
    
    private String summary;
    
    private String title;
    
    private Integer viewCounts;
    
    private Integer weight;

    private Long authorId;

    @TableField(exist = false)
    private User author;
    
    private Long bodyId;

    @TableField(exist = false)
    private ArticleBody body;
    
    private Long categoryId;

    @TableField(exist = false)
    private Category category;

    @TableField(exist = false)
    private List<Tag> tags;
    
}
