package com.yhao.SeimiCrawler.domain;

import com.yhao.SeimiCrawler.domain.entity.Data;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 杨浩
 * @create 2018-09-19 14:41
 **/
@Mapper
public interface DataMapper {

    @Select("SELECT * FROM data ORDER BY id ASC")
    List<Data> findAll();

    @Insert("INSERT data(value,type,created_time)VALUES(#{value},#{type},current_timestamp())")
    int insert(Data data);

    @Select("SELECT * FROM data WHERE id IN(#{dataIds})")
    List<Data> findByIds(@Param("dataIds") List<Integer> dataIds);

    @Select("SELECT * FROM data WHERE value=#{value}")
    Data findByValue(String value);
}
