package com.lottery.config;

import com.lottery.model.User;
import com.lottery.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShiroRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(ShiroRealm.class);


    @Autowired
    @Lazy
    UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User userinfo=(User)principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Integer usertype=userinfo.getType();
        Set<String> rolestr= new HashSet<>();
        rolestr.add(usertype.toString());
        simpleAuthorizationInfo.setRoles(rolestr);
        return simpleAuthorizationInfo;


    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        logger.info("验证当前Subject时获取到token为：" + token.toString());
        String phone=token.getUsername();
        List<User> userList=userService.findUserListByPhone(phone);

        if (userList.size()==0)
            throw new UnknownAccountException("没有找到账号信息");//没有找到账号信息
        else {
            User user= userList.get(0);
            if (user.getIsvalid()!=1)
                throw new LockedAccountException("该账号已被禁用");
            ByteSource credentialsSalt = ByteSource.Util.bytes(user.getPhone());
            return new SimpleAuthenticationInfo(user, user.getPassword(), credentialsSalt, getName());
        }


    }
}
