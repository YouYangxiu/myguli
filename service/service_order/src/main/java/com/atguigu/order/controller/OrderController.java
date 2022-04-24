package com.atguigu.order.controller;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.Result;
import com.atguigu.order.entity.Order;
import com.atguigu.order.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RequestMapping("/eduorder/order")
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    //1.生成订单的方法
    @PostMapping("createOrder/{courseId}")
    public Result saveOrder(@PathVariable String courseId, HttpServletRequest request) {
        String orderNo = orderService.createOrderService(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return Result.success().data("orderId", orderNo);
    }

    //2.根据订单id查询订单信息
    @GetMapping("getOrderInfo/{orderNo}")
    public Result getOrderInfo(@PathVariable String orderNo) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(wrapper);
        return Result.success().data("order", order);
    }

    //3.根据课程id和用户id查询订单表中的订单状态
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public Boolean isBuyCourse(@PathVariable String courseId, @PathVariable String memberId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId).eq("member_id", memberId).eq("status", 1);
        long count = orderService.count(wrapper);
        return count >= 1 ? true : false;
    }
}
