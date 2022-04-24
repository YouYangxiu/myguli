package com.atguigu.ucenter.service;

import com.atguigu.ucenter.entity.UcenterMember;
import com.atguigu.ucenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author youyangxiu
* @description 针对表【ucenter_member(会员表)】的数据库操作Service
* @createDate 2022-04-08 19:15:46
*/
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

    void register(RegisterVo registerVo);

    long countRegister(String date);
}
