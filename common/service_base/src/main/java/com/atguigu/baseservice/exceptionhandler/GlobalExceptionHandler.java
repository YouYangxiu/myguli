package com.atguigu.baseservice.exceptionhandler;


import com.atguigu.commonutils.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 */
@ControllerAdvice
//@Slf4j
public class GlobalExceptionHandler {
    @ResponseBody
    //指定使用该异常的类
    @ExceptionHandler(Exception.class)
    public Result error(Exception error) {
        error.printStackTrace();
        return Result.failure().message("执行了全局异常处理");
    }

    //特定异常处理
    @ResponseBody
    @ExceptionHandler(ArithmeticException.class)
    public Result error(ArithmeticException error) {
        error.printStackTrace();
        return Result.failure().message("除数不可以为0！");
    }

    //自定义异常
    @ResponseBody
    @ExceptionHandler(GuliException.class)
    public Result error(GuliException error) {
        //log.error(error.getMessage());
        error.printStackTrace();
        return Result.failure().code(error.getCode()).message(error.getMessage());
    }
}
