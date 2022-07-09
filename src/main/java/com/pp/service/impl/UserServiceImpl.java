package com.pp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pp.controller.UserController;
import com.pp.dao.IUserDao;
import com.pp.domain.User;
import com.pp.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<IUserDao,User> implements IUserService {
    final IUserDao userDao;

    public UserServiceImpl(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public String login(String account, String password) {
        // 按照账户和密码的形式在数据库中搜索
        HashMap<String,Object> map = new HashMap<>();
        map.put("account",account);
        map.put("password",password);
        List<User> users = userDao.selectByMap(map);
        // 如果大小为0，表示用户不存在,返回null
        if(users.size()==0)
            return null;
        // 返回身份标识
        return users.get(0).getIdentify();
    }

    @Override
    public String getUserName(String account) {
        // 按照账户和密码的形式在数据库中搜索
        HashMap<String,Object> map = new HashMap<>();
        map.put("account",account);
        List<User> users = userDao.selectByMap(map);
        // 如果大小为0，表示用户不存在,返回null
        if(users.size()==0)
            return null;
        // 返回身份标识
        return users.get(0).getName();
    }
}