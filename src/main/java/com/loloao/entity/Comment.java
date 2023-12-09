package com.loloao.entity;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Comment)表实体类
 *
 * @author makejava
 * @since 2023-12-09 19:20:52
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("me_comment")
public class Comment  {
    @TableId
    private Integer id;

    
    private String content;
    
    private Date createDate;
    
    private Integer articleId;
    
    private Long authorId;
    
    private Integer parentId;
    
    private Long toUid;
    
    private String level;
    
}
