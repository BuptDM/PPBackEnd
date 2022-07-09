package com.pp.controller;

import com.pp.controller.util.R;
import com.pp.service.IUserService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {
    // 注入 UserService Bean
    final IUserService userService;
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    // 登录接口
    @Data
    static class LoginRequest {
        public String account;
        public String password;
    }

    // 登录接口
    @PostMapping("/login")
    public R login(@RequestBody  LoginRequest request){
        String identify = userService.login(request.getAccount(),request.getPassword());
        if(identify==null){
            return R.error().message("账号不存在");
        }else {
            R r = R.ok();
            r.getData().put("identify", identify);
            return r;
        }
    }

    // 获取用户名接口
    @PostMapping("/getName")
    public R getUserName(@RequestParam String account){
        String userName = userService.getUserName(account);
        if(userName==null){
            return R.error().message("用户不存在，请检查account是否正确");
        }else {
            R r = R.ok();
            r.getData().put("userName", userName);
            return r;
        }
    }
}