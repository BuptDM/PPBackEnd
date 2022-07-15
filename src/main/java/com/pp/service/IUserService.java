package com.pp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pp.controller.util.R;
import com.pp.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface IUserService extends IService<User> {
    /**
     * 登录的请求数据类
     * */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class LoginRequest{
        public String account;
        public String password;
    }
    /**
     * 登录业务逻辑
     * @param loginRequest 登录的请求数据
     * */
    public R login(LoginRequest loginRequest);

    /**
     * 根据账号查找用户名
     * @return 返回值为null，账号对应的用户不存在;不为null，则值是该账号对应的用户名
     * @param account 需要查询用户名的账号
     * */
    public R getUserName(String account);

    /**
     * 根据账号查找用户
     * @param account 账号
     * @return 用户实例对象（如果用户不存在，返回null
     * */
    public User getUserByAccount(String account);
}