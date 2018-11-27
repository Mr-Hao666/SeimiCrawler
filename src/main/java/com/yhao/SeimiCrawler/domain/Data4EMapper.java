package com.yhao.SeimiCrawler.domain;

import com.yhao.SeimiCrawler.domain.entity.Data;
import com.yhao.SeimiCrawler.domain.entity.Data4E;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 杨浩
 * @create 2018-11-27 18:01
 **/
@Mapper
public interface Data4EMapper {


    @Select("SELECT * FROM data_20181120 WHERE type=#{type} AND created_time > #{createdTime} ORDER BY id ASC LIMIT #{pageNo},#{pageSiize}")
    List<Data4E> findByType(@Param("type") String type, @Param("createdTime") String createdTime, @Param("pageNo") Integer pageNo, @Param("pageSiize") Integer pageSize);

    @Select("SELECT * FROM data_20181120 WHERE phone_type=#{phoneType} AND created_time > #{createdTime} ORDER BY id ASC LIMIT #{pageNo},#{pageSiize}")
    List<Data4E> findByPhoneType(@Param("phoneType") String phoneType, @Param("createdTime") String createdTime,@Param("pageNo") Integer pageNo,@Param("pageSiize") Integer pageSize);

    @Select("SELECT * FROM data_20181120 WHERE  type=#{type} AND phone_type=#{phoneType} AND created_time > #{createdTime} ORDER BY id ASC LIMIT #{pageNo},#{pageSiize}")
    List<Data4E> findByPhoneTypeAndType(@Param("phoneType") String phoneType,@Param("type") String type, @Param("createdTime") String createdTime,@Param("pageNo") Integer pageNo,@Param("pageSiize") Integer pageSize);
}
