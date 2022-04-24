package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/eduservice/user")
@RestController
@CrossOrigin //解决跨域问题
@Api(tags = "登录")
public class EduLoginController {

    //login
    @PostMapping("login")
    public Result login() {
        return Result.success().data("token", "admin");
    }

    //info
    @GetMapping("info")
    public Result info() {
        return Result.success().data("name", "yyx").data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
