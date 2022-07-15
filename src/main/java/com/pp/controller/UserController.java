package com.pp.controller;

import com.pp.controller.util.R;
import com.pp.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    /**
     * 通过构造函数，注入userService
     * */
    final IUserService userService;
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    /**
    * 登录api
    * @param request 前端调用接口传递的请求体（【请求体】）
    * */
    @PostMapping("/login")
    public R login(@RequestBody IUserService.LoginRequest request, HttpServletResponse response){
        return userService.login(request);
    }

    /**
     * 根据账号查找用户名
     * */
    @PostMapping("/getName")
    public R getUserName(@RequestParam String account){
        return userService.getUserName(account);
    }
}