package com.loloao.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
    @Transactional
    public int updateUser(User user) {
        // 默认情况下，mybatis 的 update 操作返回值是记录的 matched 的条数，并不是影响的记录条数。
        int rows = userMapper.updateById(user);
        System.out.println("updateUser ==> check rows: " + rows);
        return rows;
    }

    @Override
    public void deleteUserById(Long id) {

    }

    @Override
    public void resetPassword(User reqUser) {
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", reqUser.getId());
        wrapper.set("password", reqUser.getPassword());
        wrapper.set("salt", reqUser.getSalt());
        userMapper.update(null, wrapper);
    }
}
