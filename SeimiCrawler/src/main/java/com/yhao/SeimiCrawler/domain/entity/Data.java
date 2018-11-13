package com.yhao.SeimiCrawler.domain.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel("数据")
public class Data implements Serializable {
    private Integer id;

    @ApiModelProperty("值")
    private String value;
    @ApiModelProperty("类型")
    private String type;
    @ApiModelProperty("创建时间")
    private Date createdTime;

    private static final long serialVersionUID = -1911255157732700636L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", type='" + type + '\'' +
                ", createdTime=" + createdTime +
                '}';
    }
}