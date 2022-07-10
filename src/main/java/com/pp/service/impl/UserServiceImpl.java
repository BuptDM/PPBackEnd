package com.pp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pp.dao.IUserDao;
import com.pp.domain.User;
import com.pp.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<IUserDao,User> implements IUserService {
    // 注入userDao对象
    final IUserDao userDao;
    public UserServiceImpl(IUserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * 日志对象
     * */
    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public LoginResult login(String account, String password) {
        // 按照账户在数据库中搜索
        HashMap<String,Object> map = new HashMap<>();
        map.put("account",account);
        List<User> users = userDao.selectByMap(map);
        // 如果查找到多个用户，返回登录失败，记录日志信息
        if(users.size()>1){
            log.warn("账号【"+account+"】在数据库中存在多次，请核查");
            return new LoginResult(false,"数据库异常，请联系管理员",null);
        }else if(users.size()==0){
            // 如果大小为0，表示用户不存在,返回失败信息
            return new LoginResult(false,"用户【"+account  +"】不存在。",null);
        }
        // 在数据库中存在唯一的用户，获取用户的信息
        User user = users.get(0);
        // 判断密码是否正确
        if(!user.getPassword().equals(password)){
            String message = "用户登录失败，账户【"+account+"】密码错误";
            return new LoginResult(false,message,null);
        }else{
            String message = "用户登录成功，账户【"+account+"】";
            return new LoginResult(true,message,user.getIdentify());
        }
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