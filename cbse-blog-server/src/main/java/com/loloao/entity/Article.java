package com.loloao.entity;

import java.util.Date;

import java.io.Serializable;
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
    //primary key
    @TableId
    private Integer id;

    
    private Integer commentCounts;
    
    private Date createDate;
    
    private String summary;
    
    private String title;
    
    private Integer viewCounts;
    
    private Integer weight;
    
    private Long authorId;
    
    private Long bodyId;
    
    private Integer categoryId;
    
}
