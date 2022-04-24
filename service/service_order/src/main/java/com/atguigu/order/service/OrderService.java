package com.atguigu.order.service;

import com.atguigu.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author youyangxiu
* @description 针对表【t_order(订单)】的数据库操作Service
* @createDate 2022-04-15 11:21:14
*/
public interface OrderService extends IService<Order> {

    String createOrderService(String courseId, String memberIdByJwtToken);
}
