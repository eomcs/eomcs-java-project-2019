package com.eomcs.lms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;
import com.eomcs.context.ApplicationContextListener;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.dao.MemberDao;

public class DataLoaderListener implements ApplicationContextListener {

  Connection connection;
  
  @Override
  public void contextInitialized(Map<String,Object> context) {
    System.out.println("DataLoaderListener.contextInitialized() 실행!");

    try {
      // MariaDB에 연결하기
      connection = DriverManager.getConnection(
          "jdbc:mariadb://localhost:3306/eomcs?user=eomcs&password=1111");
      context.put("connection", connection);
      System.out.println("MariaDB에 연결했습니다.");
    
      context.put("lessonDao", new LessonDao(connection));
      context.put("memberDao", new MemberDao(connection));
      context.put("boardDao", new BoardDao(connection));
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void contextDestroyed(Map<String,Object> context) {
    System.out.println("DataLoaderListener.contextInitialized() 실행!");
    
    try {
      System.out.println("MariaDB의 연결을 끊습니다.");
      // MariaDB에 연결 끊기
      connection.close();
    } catch (Exception e) {
      // 연결 끊다가 발생된 예외는 무시한다.
    }
  }
}
