package com.loloao.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (ArticleBody)表实体类
 *
 * @author makejava
 * @since 2023-12-09 19:11:44
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("me_article_body")
public class ArticleBody  {
    @TableId
    private Long id;

    
    private String content;
    
    private String contentHtml;
    
}
