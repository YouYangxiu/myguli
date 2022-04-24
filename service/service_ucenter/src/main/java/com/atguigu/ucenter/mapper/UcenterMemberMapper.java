package com.atguigu.ucenter.mapper;

import com.atguigu.ucenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author youyangxiu
* @description 针对表【ucenter_member(会员表)】的数据库操作Mapper
* @createDate 2022-04-08 19:15:46
* @Entity com.atguigu.ucenter.entity.UcenterMember
*/
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    long countRegister(String date);
}




