package com.atguigu.order.service;

import com.atguigu.order.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
* @author youyangxiu
* @description 针对表【t_pay_log(支付日志表)】的数据库操作Service
* @createDate 2022-04-15 11:46:48
*/
public interface PayLogService extends IService<PayLog> {

    Map<String, Object> createNative(String orderNo);

    Map<String, String> queryPayStatus(String orderNo);

    void updateOrderStatus(Map<String, String> map);
}
