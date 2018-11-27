package com.yhao.SeimiCrawler.service;

/**
 * @author 杨浩
 * @create 2018-11-09 21:40
 **/

import com.yhao.SeimiCrawler.domain.Data4EMapper;
import com.yhao.SeimiCrawler.domain.DataMapper;
import com.yhao.SeimiCrawler.domain.entity.Data;
import com.yhao.SeimiCrawler.domain.entity.Data4E;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class DataService {

    @Autowired
    private DataMapper dataMapper;

    @Autowired
    private Data4EMapper data4EMapper;

    public boolean isNotExist(String value) {
        return dataMapper.findByValue(value) == null;
    }

    public int create(String value, String type, String phoneType) {
        Data data = new Data();
        data.setValue(value);
        data.setType(type);
        data.setPhoneType(phoneType);
        return dataMapper.insert(data);
    }

    public List<Data4E> getList(String phoneType, String type, String createdTime, Integer pageNo, Integer pageSize) {
        if (phoneType == null && type == null) {
            return new ArrayList<>(0);
        }
        if (type == null) {
            return data4EMapper.findByPhoneType(phoneType, createdTime, pageNo, pageSize);
        } else if (phoneType == null) {
            return data4EMapper.findByType(type, createdTime, pageNo, pageSize);
        } else {
            return data4EMapper.findByPhoneTypeAndType(phoneType, type, createdTime, pageNo, pageSize);
        }
    }

    public int getCount(String phoneType, String type, String createdTime) {
        if (phoneType == null && type == null) {
            return 0;
        }
        if (type == null) {
            return dataMapper.findCountByPhoneType(phoneType, createdTime);
        } else if (phoneType == null) {
            return dataMapper.findCountByType(type, createdTime);
        } else {
            return dataMapper.findCountByPhoneTypeAndType(phoneType, type, createdTime);
        }
    }
}
