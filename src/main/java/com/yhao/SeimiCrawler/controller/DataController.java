package com.yhao.SeimiCrawler.controller;

import com.yhao.SeimiCrawler.config.ServerConfig;
import com.yhao.SeimiCrawler.domain.entity.Config;
import com.yhao.SeimiCrawler.result.BaseResult;
import com.yhao.SeimiCrawler.service.ConfigService;
import com.yhao.SeimiCrawler.service.CrawlerService;
import com.yhao.SeimiCrawler.service.DataService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;

/**
 * @author 杨浩
 * @create 2018-11-17 19:12
 **/
@Controller
@RequestMapping(value = "/data")
public class DataController {

    private final static String URL_1 = "https://operate.miaodiyun.com/operate/detailIndustrySmsLogDetail.action?businessType=industrySMS";
    private final static String URL_2 = "https://operate.miaodiyun.com/operate/detailMarketingSmsLogDetail.action?businessType=marketingSMS";
    private final static String LOGIN_URL = "https://operate.miaodiyun.com/operate/login.action";
    private volatile boolean start1 = false;
    private volatile boolean start2 = false;

    @Autowired
    private CrawlerService crawlerService;

    //短信验证码
    @ApiOperation(value = "开始获取短信验证码", httpMethod = "GET", notes = "短信验证码")
    @GetMapping(value = "/getPhone")
    public BaseResult getPhoneActivity(@NotNull(message = "内容关键字") @ApiParam("内容关键字") @RequestParam String type,
                                       @ApiParam("页码") @RequestParam Integer pageNo,
                                       @ApiParam("开始时间") @RequestParam String startTime,
                                       @ApiParam("结束时间") @RequestParam String endTime) {
        int pageSize = 600;
        Config config = new Config();
        config.setStartTime(startTime);
        config.setType(type);
        config.setPageNo(pageNo);
        config.setEndTime(endTime);
        config.setCookie(crawlerService.getSession(LOGIN_URL));
        while (!start1) {
            start1 = true;
            crawlerService.gotoWork(URL_1, pageSize, config);
        }
        start1 = false;
        return new BaseResult();
    }

    //会员营销
    @ApiOperation(value = "开始获取会员营销", httpMethod = "GET", notes = "会员营销")
    @GetMapping(value = "/getMarketing")
    public BaseResult getMarketingActivity(@NotNull(message = "内容关键字") @ApiParam("内容关键字") @RequestParam String type,
                                           @ApiParam("页码") @RequestParam Integer pageNo,
                                           @ApiParam("开始时间") @RequestParam String startTime,
                                           @ApiParam("结束时间") @RequestParam String endTime) {
        int pageSize = 600;
        Config config = new Config();
        config.setStartTime(startTime);
        config.setType(type);
        config.setPageNo(pageNo);
        config.setEndTime(endTime);
        config.setCookie(crawlerService.getSession(LOGIN_URL));
        while (!start2) {
            start2 = true;
            crawlerService.gotoWork(URL_2, pageSize, config);
        }
        start2 = false;
        return new BaseResult();
    }


}
