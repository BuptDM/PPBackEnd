package com.pp.controller;

import com.pp.controller.util.R;
import com.pp.service.IUserService;
import com.pp.service.impl.UserServiceImpl;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
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
     * 日志对象
     * */
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    /**
     * 前端调用【login api】时需要传递的请求参数
     * */
    @Data
    static class LoginRequest {
        public String account;
        public String password;
    }

    /**
    * 登录api
    * @param request 前端调用接口传递的请求体（【请求体】）
    * */
    @PostMapping("/login")
    public R login(@RequestBody  LoginRequest request){
        UserServiceImpl.LoginResult result = userService.login(request.getAccount(),request.getPassword());
        if(result.isSuccess()){
            R r = R.ok().message(result.getMessage());
            r.data("identify",result.getIdentify());
            return r;
        }else{
            return R.error().message(result.getMessage());
        }
    }

    /**
     * 根据账号查找用户名
     * */
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