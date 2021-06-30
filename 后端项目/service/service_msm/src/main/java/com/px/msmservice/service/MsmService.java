package com.px.msmservice.service;

import java.util.Map;

public interface MsmService {
    //发送短信
    boolean send(String code, String phone);
}
