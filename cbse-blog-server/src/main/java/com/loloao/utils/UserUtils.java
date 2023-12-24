package com.loloao.utils;

import com.loloao.common.Base;
import com.loloao.entity.User;
import org.apache.shiro.SecurityUtils;

public class UserUtils {

    public static User getCurrentUser() {
        System.out.println("SessionID =====" + SecurityUtils.getSubject().getSession().getId());
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute(Base.CURRENT_USER);
        return user;
    }
}