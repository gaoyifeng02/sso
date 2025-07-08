package com.gaoyifeng.sso.controller;

import com.gaoyifeng.commom.infrastructure.Result;
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
    public Result login() {
        return Result.success("登录成功");
    }
    @PostMapping("/verify")
    public Result verify(){
        return Result.success("鉴权成功");
    }

}
