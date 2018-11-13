package com.yhao.SeimiCrawler.service;

/**
 * @author 杨浩
 * @create 2018-11-09 21:40
 **/

import com.yhao.SeimiCrawler.domain.DataMapper;
import com.yhao.SeimiCrawler.domain.entity.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataService {

    @Autowired
    private DataMapper dataMapper;

    public boolean isNotExist(String value) {
        return dataMapper.findByValue(value) == null;
    }

    public int create(String value, String type) {
        Data data = null;
        data = dataMapper.findByValue(value);
        if (data == null) {
            data = new Data();
            data.setValue(value);
            data.setType(type);
            return dataMapper.insert(data);
        }
        log.info("重复数据");
        return 0;
    }
}
