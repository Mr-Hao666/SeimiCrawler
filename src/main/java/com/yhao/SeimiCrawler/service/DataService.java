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

    public int insert(Data data) {
        return dataMapper.insert(data);
    }
}
