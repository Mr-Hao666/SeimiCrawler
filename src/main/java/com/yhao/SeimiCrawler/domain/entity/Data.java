package com.yhao.SeimiCrawler.domain.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@lombok.Data
@ApiModel("数据")
public class Data implements Serializable {
    private Integer id;

    @ApiModelProperty("值")
    @Excel(name = "value", orderNum = "0")
    private String value;
    @ApiModelProperty("手机号码类型")
    private String phoneType;
    @ApiModelProperty("类型")
    private String type;
    @ApiModelProperty("创建时间")
    private Date createdTime;

    private static final long serialVersionUID = -1911255157732700636L;

}