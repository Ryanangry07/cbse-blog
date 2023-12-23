package com.loloao.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Category)表实体类
 *
 * @author makejava
 * @since 2023-12-09 19:19:58
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("me_category")
public class Category  {
    @TableId
    private Integer id;

    
    private String avatar;
    
    private String categoryname;
    
    private String description;
    
}
