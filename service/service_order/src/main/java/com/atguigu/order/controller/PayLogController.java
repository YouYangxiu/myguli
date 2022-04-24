package com.atguigu.order.controller;

import com.atguigu.commonutils.Result;
import com.atguigu.order.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RequestMapping("/eduorder/paylog")
@RestController
public class PayLogController {
    @Autowired
    private PayLogService payLogService;

    //生成微信支付二维码
    @GetMapping("createNative/{orderNo}")
    public Result createNative(@PathVariable String orderNo) {
        Map<String, Object> info = payLogService.createNative(orderNo);
        return Result.success().data("info", info);
    }

    //查询订单支付状态
    @GetMapping("queryPayStatus/{orderNo}")
    public Result queryPayStatus(@PathVariable String orderNo) {
        Map<String, String> map = payLogService.queryPayStatus(orderNo);
        if (map == null) {
            return Result.failure().message("支付失败！");
        }
        if (map.get("trade_state").equals("SUCCESS")) {
            //添加记录到支付表，更新订单表订单状态
            payLogService.updateOrderStatus(map);
            return Result.success().message("支付成功");
        }
        return Result.failure().message("支付中");
    }
}
