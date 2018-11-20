package com.yhao.SeimiCrawler.work;

import com.yhao.SeimiCrawler.config.ServerConfig;
import com.yhao.SeimiCrawler.domain.entity.Config;
import com.yhao.SeimiCrawler.service.ConfigService;
import com.yhao.SeimiCrawler.service.CrawlerService;
import com.yhao.SeimiCrawler.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
        int pageNo;
        while (!start) {
            start = true;
            if (config == null) {
                config = configService.findByName(serverConfig.getPort() + "");
            }
            pageNo = config.getPageNo();
            Element result = crawlerService.crawler(url, config.getCookie(), config.getType(), config.getStartTime(), config.getEndTime(), pageNo, pageSize);
            while (null != result) {
                Elements tbodyElements = result.getElementsByTag("tbody");
                if (null != tbodyElements) {
                    int repetition = 0;
                    Element tbodyElement = tbodyElements.get(0);
                    Elements trElements = tbodyElement.getElementsByTag("tr");
                    for (Element trElement : trElements) {
                        Element e = trElement.getElementsByTag("td").get(2);
                        if (e != null) {
                            String value = e.text();
                            if (dataService.isNotExist(value)) {
                                dataService.create(value, config.getType());
                                log.info(value);
                            } else {
                                repetition++;
                            }
                        }
                    }
                    if (repetition >= pageSize) {
                        log.info("当前页数据全部重复");
                        return;
                    }
                    log.info("获取当前页成功,重复数据" + repetition);
                } else {
                    log.info("全部数据已获取完");
                    return;
                }
                pageNo++;
                log.info("当前页码：" + pageNo);
                result = crawlerService.crawler(url, config.getCookie(), config.getType(), config.getStartTime(), config.getEndTime(), pageNo, pageSize);
            }
            start = false;
            log.info("当前无任务可执行");
            return;
        }
    }
}
