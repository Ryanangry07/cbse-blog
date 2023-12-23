package com.loloao.entity;

import java.util.Date;

import java.io.Serializable;
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
public class User  {
    @TableId
    private Long id;

    
    private String account;
    
    private Boolean admin;
    
    private String avatar;
    
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
