package com.pp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pp.controller.util.R;
import com.pp.dao.IUserDao;
import com.pp.domain.User;
import com.pp.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<IUserDao,User> implements IUserService {
    final IUserDao userDao;
    public UserServiceImpl(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public R login(LoginRequest loginRequest) {
        //获取当前用户对应的Subject
        Subject subject = SecurityUtils.getSubject();
        //执行登录方法
        subject.login(new UsernamePasswordToken(loginRequest.getAccount(),loginRequest.getPassword()));
        //判断是否认证成功
        if(subject.isAuthenticated()){
            // 设置登录有效期
            subject.getSession().setTimeout(1000*60*60*24*7);
            // 返回值给前端
            log.info("用户登录成功，account="+loginRequest.getAccount());
            String role = getUserByAccount(loginRequest.getAccount()).getRole();
            R r = R.ok().message("登录成功").data("role",role);
            r.data("AUTH_TOKEN",subject.getSession().getId().toString());
            return r;
        }else{
            log.info("用户登录失败，account="+loginRequest.getAccount());
            return R.error().message("登录失败");
        }
    }

    @Override
    public R getUserName(String account) {
        User user = getUserByAccount(account);
        if(user==null) {
            return R.error().message("用户不存在");
        }else {
            return R.ok().message("查找成功").data("userName", user.getName());
        }
    }

    @Override
    public User getUserByAccount(String account) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("account",account);
        User user = userDao.selectByMap(map).get(0);
        if(user==null){
            log.warn("用户不存在，account="+account);
            return null;
        }
        return user;
    }
}