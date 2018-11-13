package com.yhao.SeimiCrawler.util;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static com.yhao.SeimiCrawler.config.URLQueue.urlQueue;


/**
 * @author ZengXiong
 * @Description 线程工具类
 * @Date 2018/09/19 15:54
 */
@Slf4j
public class ThreadUtil {

  /** 长时间线程池的最大线程数量 */
  private static final int MAX_THREAD = 10;

  /** 长时间线程的超时时间,如果线程执行时间超过这个时间会自动释放 */
  private static final int LONG_TIMEOUT_TIME = 2;

  /** 短时间线程的超时时间,如果线程执行时间超过这个时间会自动释放 */
  private static final int SORT_TIMEOUT_TIME = 120000;

  /** 长时间线程池,用于执行短时间较长的线程,需要设置最大线程数量 */
  private static ExecutorService longTimeThreadPool = Executors.newFixedThreadPool(MAX_THREAD);

  /** 短时间线程池，用于存放执行时间较短的线程,无需设置线程的最大数量 */
  private static ExecutorService sortTimeThreadPool = Executors.newCachedThreadPool();

  /**
   * 创建一个线程,将线程放到短时间线程池中,并且启动一个守护线程监控线程超时
   *
   * @throws Exception
   */
  public static synchronized void getSortTimeOutThread(Runnable runnable) {
    //将线程提交到短时间线程池中,然后会得到一个Future
    Future<String> future = (Future<String>) sortTimeThreadPool.submit(runnable);
    //启动一个守护线程，这个线程将计算超时
    new ThreadUtil().monitorThreadTime(SORT_TIMEOUT_TIME, future);
  }

  /**
   * 创建一个线程,将线程放到长时间线程池中,并且启动一个守护线程监控线程超时
   *
   * @throws Exception
   */
  public static synchronized void getLongTimeOutThread(Runnable runnable) {
    Future<String> future = (Future<String>) longTimeThreadPool.submit(runnable);
    try {
      future.get(LONG_TIMEOUT_TIME, TimeUnit.MINUTES);
    } catch (Exception e) {
      log.error("getLongTimeOutThread ==》 异常", e);
      //判断future是否执行完成,如果超时未完成，则试着结束线程
      log.info("future.isDone:" + future.isDone() + "  future.isCancelled: " + future.isCancelled());
      if (!future.isDone() && !future.isCancelled()) {
        //false表示不允许线程执行时中断,true表示允许
        boolean result = future.cancel(true);
        log.info("当前任务是否成功中断:" + result);

        if (result) {
          log.info("当前存放需要爬取的请求url队列元素个数" + urlQueue.size());
          urlQueue.clear();
        }
      }
    }

    ////启动一个守护线程，这个线程将计算超时
    //new ThreadUtil().monitorThreadTime(LONG_TIMEOUT_TIME, future);
  }

  /**
   * 创建一个线程,将线程放到长时间线程池中,并且启动一个守护线程监控线程超时
   *
   * @throws Exception
   */
  public static synchronized void getLongTimeOutThread(Runnable runnable, int timeOutTime) throws Exception {
    //将线程提交到短时间线程池中,然后会得到一个Future
    Future<String> future = (Future<String>) longTimeThreadPool.submit(runnable);
    //启动一个守护线程，这个线程将计算超时
    new ThreadUtil().monitorThreadTime(timeOutTime, future);
  }

  /**
   * 监控线程超时守护线程,到超时时间去查看一下线程状态，如果正在执行中则停止线程（这段有问题）
   */
  private void monitorThreadTime(int timeOutTime, Future<String> future) {
    //执行守护线程
    new Thread(() -> {
      //等待一段时间，然后调用执行结果，如果没有执行完，则试着去结束
      try {
        Thread.sleep(timeOutTime);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      //判断future是否执行完成,如果超时未完成，则试着结束线程
      if (!future.isDone()) {
        //false表示不允许线程执行时中断,true表示允许
        future.cancel(true);
      }
    }).start();
  }
}
