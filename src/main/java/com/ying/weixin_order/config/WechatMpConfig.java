package com.ying.weixin_order.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Author sunmengying
 * @Date 2019/1/16 17:59
 */
@Component
public class WechatMpConfig {
    @Autowired
    private WechatAccountConfig accountConfig;
    @Bean
    public WxMpService wxMpService(){
        WxMpService mpService = new WxMpServiceImpl();
        mpService.setWxMpConfigStorage(wxMpConfigStorage());
        return mpService;
    }

    @Bean
    public WxMpConfigStorage wxMpConfigStorage(){
        WxMpInMemoryConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage();
        wxMpConfigStorage.setAppId(accountConfig.getMpAppId());
        wxMpConfigStorage.setSecret(accountConfig.getMpAppSecret());
        return wxMpConfigStorage;
    }

}
