package com.loloao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loloao.entity.User;
import com.loloao.mapper.UserMapper;
import com.loloao.service.UserService;
import com.loloao.utils.PasswordHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2023-12-09 19:28:26
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    public UserMapper userMapper;

    @Override
    public List<User> findAll() {
        return this.list();
    }

    @Override
    public User getUserByAccount(String account) {
        return lambdaQuery().eq(User::getAccount, account).one();
    }

    @Override
    public User getUserById(Long id) {
        return lambdaQuery().eq(User::getId, id).one();
    }

    /*@Override
    public Long saveUser(User user) {
        userMapper.insert(user);
        return user.getId();
    }*/

    @Override
    @Transactional
    public Long saveUser(User user) {

        PasswordHelper.encryptPassword(user);
        int index = new Random().nextInt(6) + 1;
        String avatar = "/static/user/user_" + index + ".png";

        user.setAvatar(avatar);
        userMapper.insert(user);
        return user.getId();
    }

    @Override
    public Long updateUser(User user) {
        return null;
    }

    @Override
    public void deleteUserById(Long id) {

    }
}
