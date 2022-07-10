package com.pp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pp.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

public interface IUserService extends IService<User> {
    /**
     * login返回值类
     * */
    @Data
    @AllArgsConstructor
    public static class LoginResult{
        private boolean success; // 标识是否登录成功
        private String message; // 给出成功或者失败的提示信息
        private String identify; // 登录成功需要给出用户的身份标识;登录失败，该值为Null
    }

    /**
     * 登录业务逻辑
     * @param account 用户账号
     * @param password 用户密码
     * */
    public LoginResult login(String account, String password);

    /**
     * 根据账号查找用户名
     * @return 返回值为null，账号对应的用户不存在;不为null，则值是该账号对应的用户名
     * @param account 需要查询用户名的账号
     * */
    public String getUserName(String account);
}