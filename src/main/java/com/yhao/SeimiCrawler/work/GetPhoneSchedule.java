package com.yhao.SeimiCrawler.work;

import com.yhao.SeimiCrawler.config.ServerConfig;
import com.yhao.SeimiCrawler.domain.entity.Config;
import com.yhao.SeimiCrawler.service.ConfigService;
import com.yhao.SeimiCrawler.service.CrawlerService;
import com.yhao.SeimiCrawler.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * @author 杨浩
 * @create 2018-11-09 20:02
 **/
@Component
@EnableScheduling
@Slf4j
@Async
public class GetPhoneSchedule {
    /**
     * 定时1小时
     */
    private final static long TIME = 1 * 60 * 1000 * 60 * 24;
    private final static String URL_1 = "https://operate.miaodiyun.com/operate/detailIndustrySmsLogDetail.action?businessType=industrySMS";
    private final static String URL_2 = "https://operate.miaodiyun.com/operate/detailMarketingSmsLogDetail.action?businessType=marketingSMS";
    private volatile boolean start = false;

    @Autowired
    private DataService dataService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private CrawlerService crawlerService;
    @Autowired
    private ServerConfig serverConfig;

//    //验证码
//    @Scheduled(fixedDelay = TIME)
//    public void getPhoneActivity() {
//        int pageSize = 600;
//        Config config = configService.findByName(serverConfig.getPort() + "");
//        gotoWork(URL_1,pageSize, config);
//    }

    //会员营销
//    @Scheduled(fixedDelay = TIME)
    public void getMarketingActivity() {
        int pageSize = 600;
        Config config = configService.findByName(serverConfig.getPort() + "");
        gotoWork(URL_2, pageSize, config);
    }

    private void gotoWork(String url,int pageSize, Config config) {
        while (!start) {
            start = true;
            crawlerService.gotoWork(url, pageSize, config);
        }
        start = false;
        log.info("当前无任务可执行");
    }
}
