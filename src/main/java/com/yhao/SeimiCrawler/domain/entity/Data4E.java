package com.yhao.SeimiCrawler.domain.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @author 杨浩
 * @create 2018-11-27 17:59
 **/
@lombok.Data
public class Data4E implements Serializable {
    @Excel(name = "value", orderNum = "0")
    private String value;
}
