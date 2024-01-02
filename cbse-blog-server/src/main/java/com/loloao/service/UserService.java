package com.loloao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.loloao.entity.User;

import java.util.List;


/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2023-12-09 19:28:26
 */
public interface UserService extends IService<User> {

    List<User> findAll();

    User getUserByAccount(String account);

    User getUserById(Long id);

    Long saveUser(User user);

    int updateUser(User user);

    void deleteUserById(Long id);

    void resetPassword(User reqUser);
}
