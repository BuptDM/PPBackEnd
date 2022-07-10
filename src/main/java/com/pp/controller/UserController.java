package com.pp.controller;

import com.pp.controller.util.R;
import com.pp.service.IUserService;
import com.pp.service.impl.UserServiceImpl;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
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
        log.info(result.getMessage());
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
            String message = "用户不存在，请检查account【"+account+"】是否正确";
            log.info(message);
            return R.error().message(message);
        }else {
            R r = R.ok().message("success");
            r.getData().put("userName", userName);
            log.info("成功查找用户名，账号【"+account+"】");
            return r;
        }
    }
}