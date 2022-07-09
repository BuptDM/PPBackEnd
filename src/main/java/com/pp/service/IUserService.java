package com.pp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pp.controller.UserController;
import com.pp.domain.User;

public interface IUserService extends IService<User> {
    public String login(String account, String password);
    public String getUserName(String account);
}