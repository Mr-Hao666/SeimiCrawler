package com.yhao.SeimiCrawler.service;

import com.yhao.SeimiCrawler.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;
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

    public Element crawler(String url, String cookie, String type, String startTime, String
            endTime, int pageNo, int pageSize) {
        Element result = null;
        try {
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
            Map<String, Object> paramMap = new HashMap<>();

            paramMap.put("pageSize", pageSize);
            paramMap.put("page", pageNo);
            paramMap.put("submitByNavbar", true);
            paramMap.put("startTime", startTime);
            paramMap.put("endTime", endTime);
            paramMap.put("content", type);
            String uriDetail = HttpClientUtil.postData(url, headerMap, paramMap);
            Document doc = Jsoup.parse(uriDetail);
            result = doc.getElementsByClass("retrieve-list").first();
        } catch (Exception e) {
            e.printStackTrace();
            log.info("爬取数据异常:" + e);
        }
        return result;
    }

    public static String pickData(String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(url);
            try (CloseableHttpResponse response = httpclient.execute(httpget)) {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                // 打印响应状态
                if (entity != null) {
                    return EntityUtils.toString(entity, "utf-8");
                }
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
