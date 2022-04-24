package com.atguigu.ucenter.service.impl;

import com.atguigu.baseservice.exceptionhandler.GuliException;
import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.ucenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.ucenter.entity.UcenterMember;
import com.atguigu.ucenter.service.UcenterMemberService;
import com.atguigu.ucenter.mapper.UcenterMemberMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author youyangxiu
 * @description 针对表【ucenter_member(会员表)】的数据库操作Service实现
 * @createDate 2022-04-08 19:15:46
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember>
        implements UcenterMemberService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String login(UcenterMember member) {
        //获取登录手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001, "用户名或密码不能为空!");
        }

        //判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        UcenterMember record = baseMapper.selectOne(wrapper);
        if (record == null) {
            throw new GuliException(20001, "用户不存在！请重新输入");
        }
        //判断密码是否正确,需要先加密
        //加密方式：MD5
        password = MD5.encrypt(password);
        if (!password.equals(record.getPassword())) {
            throw new GuliException(20001, "密码错误，请重新输入！");
        }
        //验证用户是否被禁用
        if (record.getIsDisabled() == 1) {
            throw new GuliException(20001, "您已被封号！");
        }
        //登录成功，生成token
        String token = JwtUtils.getJwtToken(record.getId(), record.getNickname());
        return token;
    }

    @Override
    public void register(RegisterVo registerVo) {
        String code = registerVo.getCode();
        String phone = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(phone) || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001, "数据不可以为空！");
        }
        //检验验证码
//        System.out.println(redisTemplate.opsForValue().get(phone));
//        System.out.println(code);
//        System.out.println(code.equals(redisTemplate.opsForValue().get(phone).toString()));
        if (!code.equals(redisTemplate.opsForValue().get(phone).toString())) {
            throw new GuliException(20001, "验证码错误！请重新输入");
        }
        //验证用户是否已经存在
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", phone);
        Long count = baseMapper.selectCount(wrapper);
        if (count != 0) {
            throw new GuliException(20001, "用户已存在！");
        }
        UcenterMember member = new UcenterMember();
        member.setMobile(phone);
        member.setPassword(MD5.encrypt(password));
        member.setNickname(nickname);
        member.setIsDisabled(0);
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        baseMapper.insert(member);
    }

    @Override
    public long countRegister(String date) {
        baseMapper.countRegister(date);
        return 0;
    }
}




