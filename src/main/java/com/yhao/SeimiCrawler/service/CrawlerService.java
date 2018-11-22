package com.yhao.SeimiCrawler.service;

import com.yhao.SeimiCrawler.config.ServerConfig;
import com.yhao.SeimiCrawler.domain.entity.Config;
import com.yhao.SeimiCrawler.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZengXiong
 * @Description 爬虫service
 * @Date 2018/09/19 15:36
 */
@Component
@Slf4j
public class CrawlerService {
    @Autowired
    private ConfigService configService;
    @Autowired
    private ServerConfig serverConfig;
    @Autowired
    private DataService dataService;

    private static final String COOKIE = "UM_distinctid=165efee581f0-029c8493ecd82a-333b5602-1fa400-165efee5820df; ifLoginRememberName=checked; Qs_lvt_122950=1537328699%2C1539670075%2C1539832521%2C1540372738%2C1542008522; Qs_pv_122950=2192971793787125800%2C306022982407525250%2C3193578770323489300%2C2424251150446799000%2C2367439715378550000; Hm_lvt_e1385c7969f3f5d9ffa4c00b2264865a=1539670076,1539832521,1540372739,1542008522; loginRememberedName=3110138680%40qq.com";
    private static final String USERNAME = "3110138680@qq.com";
    private static final String PASSWORD = "Md18503052788";

    public void gotoWork(String url, int pageSize, Config config) {
        if (config == null) {
            config = configService.findByName(serverConfig.getPort() + "");
        }
        int pageNo = config.getPageNo();
        Element result = crawler(url, config.getCookie(), config.getType(), config.getStartTime(), config.getEndTime(), pageNo, pageSize);
        while (null != result) {
            Elements tbodyElements = result.getElementsByTag("tbody");
            if (null != tbodyElements) {
                int repetition = 0;
                Element tbodyElement = tbodyElements.get(0);
                Elements trElements = tbodyElement.getElementsByTag("tr");
                if (trElements == null || trElements.size() <= 0) {
                    log.info("已获取全部数据");
                    return;
                }
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
            result = crawler(url, config.getCookie(), config.getType(), config.getStartTime(), config.getEndTime(), pageNo, pageSize);
        }

        log.info("当前无任务可执行");
        return;
    }

    private Map<String, String> assemblyHeaderParameters(String url, String cookie) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        headerMap.put("Accept-Encoding", "gzip, deflate, br");
        headerMap.put("Accept-Language", "zh-CN,zh;q=0.9");
        headerMap.put("Cache-Control", "no-cache");
        headerMap.put("Connection", "keep-alive");
        headerMap.put("Content-Type", "application/x-www-form-urlencoded");
        headerMap.put("Cookie", cookie);
        headerMap.put("Host", "operate.miaodiyun.com");
        headerMap.put("Origin", "https://operate.miaodiyun.com");
        headerMap.put("Pragma", "no-cache");
        headerMap.put("Referer", url);
        headerMap.put("Upgrade-Insecure-Requests", "1");
        headerMap.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36)");
        return headerMap;
    }

    private Map<String, Object> assemblyParameters(String type, String startTime, String
            endTime, int pageNo, int pageSize) {
        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("pageSize", pageSize);
        paramMap.put("page", pageNo);
        paramMap.put("submitByNavbar", true);
//            paramMap.put("phoneType", 2);
//            paramMap.put("channelId", 265);
        paramMap.put("startTime", startTime);
        paramMap.put("endTime", endTime);
        paramMap.put("content", type);
        return paramMap;
    }

    private Map<String, Object> assemblyLoginParameters() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userName", USERNAME);
        paramMap.put("password", PASSWORD);
        paramMap.put("rememberName", "on");
        paramMap.put("bnt", "登录");
        return paramMap;
    }

    public Element crawler(String url, String cookie, String type, String startTime, String
            endTime, int pageNo, int pageSize) {
        Element result = null;
        try {
            String uriDetail = HttpClientUtil.postData(url, assemblyHeaderParameters(url, cookie), assemblyParameters(type
                    , startTime
                    , endTime
                    , pageNo
                    , pageSize));
            Document doc = Jsoup.parse(uriDetail);
            result = doc.getElementsByClass("retrieve-list").first();
        } catch (Exception e) {
            e.printStackTrace();
            log.info("爬取数据异常:" + e);
        }
        return result;
    }

    public String getSession(String urlString) {

        String session = null;
        try {
            session = HttpClientUtil.postHeader(urlString,
                    assemblyHeaderParameters(urlString, COOKIE),
                    assemblyLoginParameters());
        } catch (Exception e) {
            e.printStackTrace();
            log.info("获取SESSION异常:" + e);
        }
        return "SESSION=" + session + ";" + COOKIE;
    }
}
