package com.eomcs.lms;

import java.util.Map;
import com.eomcs.context.ApplicationContextListener;
import com.eomcs.lms.proxy.BoardDaoProxy;
import com.eomcs.lms.proxy.LessonDaoProxy;
import com.eomcs.lms.proxy.MemberDaoProxy;

public class DataLoaderListener implements ApplicationContextListener {

  @Override
  public void contextInitialized(Map<String,Object> context) {
    System.out.println("DataLoaderListener.contextInitialized() 실행!");

    try {
      
      String host = "localhost";
      int port = 8888;
      
      context.put("lessonDao", new LessonDaoProxy(host, port));
      context.put("memberDao", new MemberDaoProxy(host, port));
      context.put("boardDao", new BoardDaoProxy(host, port));
      
    } catch (Exception e) {
      System.out.println("서버 연결 오류!");
    }
  }

  @Override
  public void contextDestroyed(Map<String,Object> context) {
    System.out.println("DataLoaderListener.contextInitialized() 실행!");
  }
}
