package com.pp.config;

import com.pp.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserRealm extends AuthorizingRealm {
    final IUserService userService;
    public UserRealm(IUserService userService) {
        this.userService = userService;
    }

    //授权方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    //认证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String account = (String) authenticationToken.getPrincipal();
        String password = userService.getUserByAccount(account).getPassword();
        return new SimpleAuthenticationInfo(account,password,getName());
    }
}