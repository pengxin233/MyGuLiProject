package com.px.msmservice.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MsmUtil implements InitializingBean {
    @Value("${tencent.secretId}")
    private String secretId;
    @Value("${tencent.secretKey}")
    private String secretKey;
    @Value("${tencent.sdkAppId}")
    private String sdkAppId;
    @Value("${tencent.signName}")
    private String signName;
    @Value("${tencent.templateId}")
    private String templateId;


    public static String SECRECT_ID;
    public static String SECRECT_KEY;
    public static String SDKAPP_ID;
    public static String SIGN_NAME;
    public static String TEMPLATE_ID;
    @Override
    public void afterPropertiesSet() throws Exception {
            SECRECT_ID = secretId;
            SECRECT_KEY = secretKey;
            SDKAPP_ID = sdkAppId;
            SIGN_NAME = signName;
            TEMPLATE_ID = templateId;
    }
}
