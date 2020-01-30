package com.cris.myshop.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping("/")
    public String toLoginPage() {
        return "login";
    }

    @RequestMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, @RequestParam(defaultValue = "") String rememberMe) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        if ("1".equals(rememberMe)) {
            token.setRememberMe(true);
        }
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            logger.info("用户不存在！");
            return "redirect:/";
        } catch (IncorrectCredentialsException e) {
            logger.info("密码错误！");
            return "redirect:/";
        }
        return "redirect:/list";
    }

    @RequestMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }
}
