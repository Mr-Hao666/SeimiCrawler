package com.yhao.SeimiCrawler.domain;

import com.yhao.SeimiCrawler.domain.entity.Data;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author 杨浩
 * @create 2018-09-19 14:41
 **/
@Mapper
public interface DataMapper {

    @Select("SELECT * FROM data_20181206 WHERE phone=#{value}")
    Data findByValue(String value);

    @Insert("INSERT INTO `data_20181206` (`email`, `channel`, `phone`, `phone_type`, `province`, `city`, `begin_time`, `end_time`, `content`) VALUES (#{email}, #{channel}, #{phone}, #{phoneType}, #{province}, #{city}, #{beginTime}, #{endTime}, #{content})")
    int insert(Data data);
}
