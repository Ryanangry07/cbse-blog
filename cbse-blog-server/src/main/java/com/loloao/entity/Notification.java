package com.loloao.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("me_notification")
public class Notification {
    //primary key
    @TableId
    private Long id;

    private Long uid;

    private Boolean readStatus;

    private String title;

    private String content;

    // 0: @me notifications (comment,star,reply)
    // 1: system notifications
    private Integer type;

    private Long fromUid;

    @TableField(exist = false)
    private String fromUser;

    @JSONField(format = "yyyy.MM.dd HH:mm")
    private Date createDate;

    // should have notification page number

    // when update notification table?

    /*
    1. when user click star to article
    in progress => testing => done

    2. when user publish an article
    pending => in progress => testing => done

    3. when user edit article
    pending => in progress => testing => done

    4. when user delete article
    pending => in progress => testing => done

    5. when user's article got commented
    pending => testing

    6. when user's article got starred
    pending => in progress => testing => done

    7. when user's comment got replied
    pending => testing
     */
}
