package com.yhao.SeimiCrawler.service;

import com.yhao.SeimiCrawler.domain.ConfigMapper;
import com.yhao.SeimiCrawler.domain.entity.Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author 杨浩
 * @create 2018-11-10 9:26
 **/
@Component
@Slf4j
public class ConfigService{

 private ConfigMapper configMapper;
    public Config findByName(String name) {
        return configMapper.findByName(name);
    }
}
