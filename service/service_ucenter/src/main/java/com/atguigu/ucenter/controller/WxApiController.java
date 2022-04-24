package com.atguigu.ucenter.controller;

import com.atguigu.baseservice.exceptionhandler.GuliException;
import com.atguigu.commonutils.JwtUtils;
import com.atguigu.ucenter.entity.UcenterMember;
import com.atguigu.ucenter.service.UcenterMemberService;
import com.atguigu.ucenter.utils.ConstantWxUtils;
import com.atguigu.ucenter.utils.HttpClientUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@CrossOrigin
@Controller//只是请求地址不需要返回数据
@RequestMapping("/api/ucenter/wx")
public class WxApiController {
    @Autowired
    private UcenterMemberService ucenterMemberService;

    //1.生成微信扫描的二维码
    @GetMapping("login")
    public String getWxCode() {
        //固定地址，后面拼接参数
//        String url = "https://open.weixin.qq.com/connect/qrconnect?appid=" + ConstantWxUtils.WX_OPEN_APP_ID + "&response_type=code"
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect"
                + "?appid=%s"
                + "&redirect_uri=%s"
                + "&response_type=code"
                + "&scope=snsapi_login"
                + "&state=%s"
                + "#wechat_redirect";
        //对redirect_url进行URLEncoder编码
        String redirect_url = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            URLEncoder.encode(redirect_url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = String.format(baseUrl, ConstantWxUtils.WX_OPEN_APP_ID, ConstantWxUtils.WX_OPEN_REDIRECT_URL,
                "atguigu");
        //请求微信地址
        return "redirect:" + url;
    }

    //获取扫描人的信息
    @GetMapping("callback")
    public String callback(String code, String state) {
        try {

//            System.out.println(code);
//            System.out.println(state);
            //1.获取code值（临时票据）类似于验证码
            //2.拿着code请求微信的固定地址，得到两个值access_token和openid
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            //3.拼接3个参数
            String accessTokenUrl = String.format(baseAccessTokenUrl, ConstantWxUtils.WX_OPEN_APP_ID, ConstantWxUtils.WX_OPEN_APP_SECRET, code);
            //4.使用httpClientUtils发送请求，得到对应的access_token和openid
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            System.out.println(accessTokenInfo);
            //5.将结果转化为map集合
            //使用Gson转化工具
            Gson gson = new Gson();
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String openid = (String) mapAccessToken.get("openid");
            String access_token = (String) mapAccessToken.get("access_token");

            //8.判断数据库是否存在openid
            QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
            wrapper.eq("openid", openid);
            UcenterMember member = ucenterMemberService.getOne(wrapper);
            if (member == null) {
                //6.根据openid和access_token去请求固定地址得到扫描人的信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(baseUserInfoUrl, access_token, openid);
                //发送请求
                String userInfo = HttpClientUtils.get(userInfoUrl);
                System.out.println(userInfo);
                //7.获取用户信息
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String) userInfoMap.get("nickname");
                String headimgurl = (String) userInfoMap.get("headimgurl");
                member = new UcenterMember();
                member.setAvatar(headimgurl);
                member.setNickname(nickname);
                member.setOpenid(openid);
                ucenterMemberService.save(member);
            }
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            return "redirect:http://localhost:3000?token=" + jwtToken;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001, "登录失败！");
        }
    }
}
