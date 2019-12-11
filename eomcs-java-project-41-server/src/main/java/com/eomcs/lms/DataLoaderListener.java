package com.eomcs.lms;

import java.util.Map;
import com.eomcs.context.ApplicationContextListener;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.dao.PhotoFileDao;
import com.eomcs.sql.DataSource;
import com.eomcs.sql.TransactionManager;

public class DataLoaderListener implements ApplicationContextListener {

  DataSource dataSource;
  
  @Override
  public void contextInitialized(Map<String,Object> context) {
    System.out.println("DataLoaderListener.contextInitialized() 실행!");

    try {
      dataSource = new DataSource(
          "jdbc:mariadb://localhost:3306/eomcs",
          "eomcs", "1111");
      
      context.put("lessonDao", new LessonDao(dataSource));
      context.put("memberDao", new MemberDao(dataSource));
      context.put("boardDao", new BoardDao(dataSource));
      context.put("photoBoardDao", new PhotoBoardDao(dataSource));
      context.put("photoFileDao", new PhotoFileDao(dataSource));
      
      context.put("transactionManager", new TransactionManager(dataSource));
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void contextDestroyed(Map<String,Object> context) {
    System.out.println("DataLoaderListener.contextInitialized() 실행!");
    dataSource.close();
  }
}
