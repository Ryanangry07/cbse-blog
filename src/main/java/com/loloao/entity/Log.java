package com.loloao.entity;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Log)表实体类
 *
 * @author makejava
 * @since 2023-12-09 19:29:53
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_log")
public class Log  {
    @TableId
    private Integer id;

    
    private Date createDate;
    
    private String ip;
    
    private String method;
    
    private String module;
    
    private String nickname;
    
    private String operation;
    
    private String params;
    
    private Long time;
    
    private Long userid;
    
}
