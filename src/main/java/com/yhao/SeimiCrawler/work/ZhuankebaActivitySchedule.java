//package com.yhao.SeimiCrawler.work;
//
////import cn.ycmedia.common.utils.ThreadUtil;
////import cn.ycmedia.service.CrawlerService;
//import java.net.URL;
//
//import com.yhao.SeimiCrawler.service.CrawlerService;
//import com.yhao.SeimiCrawler.util.StringUtil;
//import com.yhao.SeimiCrawler.util.ThreadUtil;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//
///**
// * @author ZengXiong
// * @Description 赚客吧 活动线报 定时任务
// * @Date 2018/09/21 11:18
// */
//@Component
//@EnableScheduling
//@Service
//public class ZhuankebaActivitySchedule {
//
//  private Logger logger = LoggerFactory.getLogger("monitor");
//
//  /**
//   * 定时5分钟
//   */
//  private final static long FIVE_MINUTES = 5 * 60 * 1000;
//
//  private volatile boolean start = false;
//
//  @Autowired
//  private CrawlerService crawlerService;
//
//  @Scheduled(fixedDelay = FIVE_MINUTES)
//  public void getZhuankebaActivity() {
////    logger.info("----------开始爬取赚客吧活动线报------------------");
////    Long startTime = System.currentTimeMillis();
////    try {
//      String baseUri = "https://operate.miaodiyun.com/operate/detailIndustrySmsLogDetail.action?businessType=industrySMS";
////      Document doc = Jsoup.parse(new URL(baseUri).openStream(), "UTF-8", baseUri);
////      Element result = doc.getElementsByClass("retrieve-list").first();
////      if (null != result) {
////        Elements tbodyElements = result.getElementsByTag("tbody");
////        if (null != tbodyElements) {
////          for (int i = 2; i < tbodyElements.size(); i++) {
////            Element e = result.getElementsByTag("tbody").get(i).getElementsByClass("new").select("a").get(0);
////            if (e != null) {
//////              urlQueue.offer(e.attr("abs:href") + "&mobile=no");
////            }
////          }
////        }
////      }
////      logger.info("列表获取完成,总长度:" + urlQueue.size() + ",共用时:" + (System.currentTimeMillis() - startTime) + "ms------------------");
//
//      if (!start) {
//        start = true;
////        if (!urlQueue.isEmpty()) {
//          try {
//            ThreadUtil.getLongTimeOutThread(() -> {
//              while (start) {
//                {
////                  String url = urlQueue.poll();
//                  if (StringUtil.notEmpty(baseUri)) {
//                    crawlerService.crawler(baseUri);
//                  }
//
////                  if (urlQueue.isEmpty()) {
////                    start = false;
////                    logger.info("----------爬取赚客吧活动线报入库完成,共用时" + (System.currentTimeMillis() - startTime) + "ms------------------");
////                  }
//                }
//              }
//            });
//          } catch (Exception e) {
//            logger.error("入库出错:" + e);
//          }
//        } else {
//          start = false;
//          logger.info("当前无任务可执行");
//        }
////      } else {
////        logger.info("当前任务进行中！");
////      }
////    } catch (Exception e) {
////      logger.error("爬取数据出错", e);
////    }
//  }
//}
