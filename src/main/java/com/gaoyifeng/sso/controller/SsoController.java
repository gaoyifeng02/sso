package com.gaoyifeng.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author gaoyifeng
 * @Classname SsoController
 * @Description TODO
 * @Date 2025/6/14 17:26
 * @Created by gaoyifeng
 */
@Controller
@ResponseBody
public class SsoController {
    @PostMapping("/login")
    public String login() {
        return "登录成功";
    }
    @PostMapping("/verify")
    public String verify() {
        return "鉴权成功";
    }

}
