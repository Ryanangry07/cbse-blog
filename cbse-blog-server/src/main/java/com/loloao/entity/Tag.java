package com.loloao.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Tag)表实体类
 *
 * @author makejava
 * @since 2023-12-09 19:10:18
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("me_tag")
public class Tag  {
    @TableId
    private Long id;

    
    private String avatar;
    
    private String tagname;
    
}
