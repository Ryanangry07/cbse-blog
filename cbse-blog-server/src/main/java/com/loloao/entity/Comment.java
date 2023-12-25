package com.loloao.entity;

import java.util.Date;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    private Long id;

    
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createDate;
    
    private Long articleId;

    @TableField(exist = false)
    private Article article;
    
    private Long authorId;

    @TableField(exist = false)
    private User author;
    
    private Long parentId;

    @TableField(exist = false)
    private Comment parent;

    @TableField(exist = false)
    private List<Comment> childrens;
    
    private Long toUid;

    @TableField(exist = false)
    private User toUser;
    
    private String level;
    
}
