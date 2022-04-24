package com.atguigu.msmservice.service;

import java.util.HashMap;

public interface MsmService {
    boolean senMessage(HashMap<String, Object> param, String phone);
}
