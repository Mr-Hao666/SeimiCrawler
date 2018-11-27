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

    @Select("SELECT * FROM data_20181120 ORDER BY id ASC")
    List<Data> findAll();

    @Insert("INSERT data_20181120(value,type,created_time)VALUES(#{value},#{type},current_timestamp())")
    int insert(Data data);

    @Select("SELECT * FROM data_20181120 WHERE id IN(#{dataIds})")
    List<Data> findByIds(@Param("dataIds") List<Integer> dataIds);

    @Select("SELECT * FROM data_20181120 WHERE value=#{value}")
    Data findByValue(String value);

    @Select("SELECT count(id) FROM data_20181120 WHERE type=#{type} AND created_time > #{createdTime}")
    int findCountByType(@Param("type") String type, @Param("createdTime") String createdTime);

    @Select("SELECT count(id) FROM data_20181120 WHERE phone_type=#{phoneType} AND created_time > #{createdTime}")
    int findCountByPhoneType(@Param("phoneType") String phoneType, @Param("createdTime") String createdTime);

    @Select("SELECT count(id) FROM data_20181120 WHERE type=#{type} AND phone_type=#{phoneType} AND created_time > #{createdTime}")
    int findCountByPhoneTypeAndType(@Param("phoneType") String phoneType,@Param("type") String type, @Param("createdTime") String createdTime);
}
