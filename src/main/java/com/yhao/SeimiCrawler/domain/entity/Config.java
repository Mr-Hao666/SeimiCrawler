package com.yhao.SeimiCrawler.domain.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 杨浩
 * @create 2018-11-10 9:21
 **/
@Data
@ApiModel("配置")
public class Config implements Serializable {

    private Integer id;
    @ApiModelProperty("项目名称")
    private String name;
    @ApiModelProperty("cookie")
    private String cookie;
    @ApiModelProperty("类型")
    private String type;
    @ApiModelProperty("页码")
    private Integer pageNo;
    @ApiModelProperty("开始时间")
    private String startTime;
    @ApiModelProperty("结束时间")
    private String endTime;
    private static final long serialVersionUID = -7262583984724922201L;

}
