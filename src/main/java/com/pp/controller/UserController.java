package com.pp.controller;

import com.pp.controller.util.R;
import com.pp.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    public R login(@RequestBody IUserService.LoginRequest request){
        return userService.login(request);
    }

    /**
     * 重定向api，如果未认证或者未授权访问，直接重定向到登录界面
     * */
    @GetMapping("/redirect")
    public R redirect() {
        return R.error().message("尚未登陆，请先登录后访问");
    }

    /**
     * 根据账号查找用户名
     * */
    @PostMapping("/getName")
    public R getUserName(@RequestParam String account){
        return userService.getUserName(account);
    }
}