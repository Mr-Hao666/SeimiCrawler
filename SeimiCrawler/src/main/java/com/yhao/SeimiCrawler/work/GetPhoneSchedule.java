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
@Service
@Slf4j
public class GetPhoneSchedule {
    /**
     * 定时1小时
     */
    private final static long TIME = 1 * 60 * 1000 * 60 * 24;
    private final static String TYPE = "贷";
    private final static String COOKIE = "SESSION=19103c14-e8ed-4604-a919-4a562de531a6; UM_distinctid=165e267ba3d51-05ecb9c37acb89-9393265-100200-165e267ba3e22a; Qs_lvt_122950=1537101773; Hm_lvt_e1385c7969f3f5d9ffa4c00b2264865a=1537101774; Qs_pv_122950=1952889362640003800%2C2785160688135454000%2C2392956234183804400%2C1523922986538885000%2C3306519988348241000; ifLoginRememberName=checked; loginRememberedName=1489572523%40qq.com";
    private final static String URL = "https://operate.miaodiyun.com/operate/detailIndustrySmsLogDetail.action?businessType=industrySMS";

    private volatile boolean start = false;

    @Autowired
    private DataService dataService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private CrawlerService crawlerService;
    @Autowired
    private ServerConfig serverConfig;

    @Scheduled(fixedDelay = TIME)
    public void getPhoneActivity() {
        int pageNo = 1;
        int pageSize = 200;
        Config config = configService.findByName(serverConfig.getPort() + "");
        while (!start) {
            start = true;
            if (config == null) {
                config = configService.findByName(serverConfig.getPort() + "");
            }
            pageNo = config.getPageNo();
            Element result = crawlerService.crawler(URL, config.getCookie(), config.getType(), config.getStartTime(), config.getEndTime(), pageNo, pageSize);
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
                    }
                    log.info("获取当前页成功,重复数据" + repetition);
                }
                pageNo++;
                log.info("当前页码：" + pageNo);
                result = crawlerService.crawler(URL, config.getCookie(), config.getType(), config.getStartTime(), config.getEndTime(), pageNo, pageSize);
            }
            start = false;
            log.info("当前无任务可执行");
        }
    }
}
