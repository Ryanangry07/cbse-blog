package com.loloao.entity;

import java.util.Date;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (User)表实体类
 *
 * @author makejava
 * @since 2023-12-09 19:28:26
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user")
public class User implements Serializable {
    @TableId
    private Long id;

    
    private String account;
    
    private Boolean admin;
    
    private String avatar;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @JSONField(format = "yyyy.MM.dd HH:mm")
    private Date createDate;
    
    private String deleted;
    
    private String email;
    
    private Date lastLogin;
    
    private String mobilePhoneNumber;
    
    private String nickname;
    
    private String password;
    
    private String salt;
    
    private String status;
    
}
