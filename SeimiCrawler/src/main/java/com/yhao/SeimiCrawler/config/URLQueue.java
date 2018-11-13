package com.yhao.SeimiCrawler.config;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author 杨浩
 * @create 2018-11-09 17:13
 **/
public class URLQueue {

    /**
     * 存放需要爬取的请求url
     */
    public static BlockingQueue<String> urlQueue = new LinkedBlockingDeque<>();
}
