package com.atguigu.commonutils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
    SUCCESS(20000),
    ERROR(20001);
    private Integer code;

}
