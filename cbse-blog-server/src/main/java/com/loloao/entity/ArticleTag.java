package com.loloao.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (ArticleTag)表实体类
 *
 * @author makejava
 * @since 2023-12-09 19:12:09
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("me_article_tag")
public class ArticleTag  {

    
    private Long articleId;
    
    private Long tagId;
    
}
