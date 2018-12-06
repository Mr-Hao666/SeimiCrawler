package com.yhao.SeimiCrawler.domain;

import com.yhao.SeimiCrawler.domain.entity.Config;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author 杨浩
 * @create 2018-11-10 9:25
 **/
@Mapper
public interface ConfigMapper{

    @Select("SELECT * FROM config WHERE name=#{name}")
    Config findByName(String name);
}
