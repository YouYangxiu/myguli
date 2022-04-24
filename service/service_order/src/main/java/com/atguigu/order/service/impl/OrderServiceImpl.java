package com.atguigu.order.service.impl;

import com.atguigu.commonutils.ordervo.CourseWebOrder;
import com.atguigu.commonutils.ordervo.UcenterMemberOrder;
import com.atguigu.order.client.CourseClient;
import com.atguigu.order.client.UcenterClient;
import com.atguigu.order.utils.OrderNoUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.order.entity.Order;
import com.atguigu.order.service.OrderService;
import com.atguigu.order.mapper.OrderMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author youyangxiu
 * @description 针对表【t_order(订单)】的数据库操作Service实现
 * @createDate 2022-04-15 11:21:14
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
        implements OrderService {
    @Autowired
    private CourseClient courseClient;
    @Autowired
    private UcenterClient ucenterClient;

    //1.生成订单的方法
    @Override
    public String createOrderService(String courseId, String memberId) {
        //通过远程调用根据课程id获取课程信息
        CourseWebOrder courseInfoOrder = courseClient.getCourseInfoOrder(courseId);
        //通过远程调用根据用户id获取用户信息
        UcenterMemberOrder userInfoOrder = ucenterClient.getUserInfoOrder(memberId);
        Order order = new Order();
        order.setOrderNo(OrderNoUtils.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberId);
        order.setNickname(userInfoOrder.getNickname());
        order.setStatus(0);
        order.setPayType(1);
        baseMapper.insert(order);

//        BeanUtils.copyProperties(userInfoOrder, order);
//        BeanUtils.copyProperties(courseInfoOrder, order);
        return order.getOrderNo();
    }
}




