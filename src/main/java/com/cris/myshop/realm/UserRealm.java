package com.cris.myshop.realm;

import com.cris.myshop.dao.UserDao;
import com.cris.myshop.entity.User;
import com.cris.myshop.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用于用户登录认证
 */
public class UserRealm extends AuthenticatingRealm {
    @Autowired
    private UserService userService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String)authenticationToken.getPrincipal();
        if (username == null || "".equals(username)) {
            throw new UnknownAccountException("用户名为空！");
        }
        User info = new User();
        info.setUsername(username);
        User user = userService.getUserByUsername(info);
        if (user == null) {
            return null;
        }
        return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), ByteSource.Util.bytes(user.getSalt()), this.getName());
    }
}
