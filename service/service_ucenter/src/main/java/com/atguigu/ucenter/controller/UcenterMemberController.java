package com.atguigu.ucenter.controller;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.Result;
import com.atguigu.commonutils.ordervo.UcenterMemberOrder;
import com.atguigu.ucenter.entity.UcenterMember;
import com.atguigu.ucenter.entity.vo.RegisterVo;
import com.atguigu.ucenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService memberService;

    //登录
    @PostMapping("login")
    public Result loginUser(@RequestBody UcenterMember member) {
        //返回token值 用jwt生成
        String token = memberService.login(member);
        return Result.success().data("token", token);
    }

    //注册
    @PostMapping("register")
    public Result registerUser(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return Result.success();
    }

    //根据token获取用户信息
    @GetMapping("getMemberInfo")
    public Result getMemberInfo(HttpServletRequest request) {
        //根据token获取用户id
        String id = JwtUtils.getMemberIdByJwtToken(request);
        //根据用户id获取用户信息
        UcenterMember member = memberService.getById(id);
        return Result.success().data("userInfo", member);
    }

    //根据用户id获取用户信息
    @GetMapping("getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id) {
        UcenterMember userInfo = memberService.getById(id);
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(userInfo, ucenterMemberOrder);
        return ucenterMemberOrder;
    }

    //查询某一天的注册人数，用于统计分析模块远程调用
    @GetMapping("countRegister/{date}")
    public long countRegister(@PathVariable String date) {
        long result = memberService.countRegister(date);
        return result;
    }
}
