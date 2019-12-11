package com.eomcs.lms;

import java.io.InputStream;
import java.util.Map;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.context.ApplicationContextListener;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.dao.PhotoFileDao;
import com.eomcs.sql.MybatisDaoFactory;
import com.eomcs.sql.SqlSessionFactoryProxy;
import com.eomcs.sql.TransactionManager;

public class DataLoaderListener implements ApplicationContextListener {

  @Override
  public void contextInitialized(Map<String,Object> context) {
    System.out.println("DataLoaderListener.contextInitialized() 실행!");

    try {
      String resource = "com/eomcs/lms/conf/mybatis-config.xml";
      InputStream inputStream = Resources.getResourceAsStream(resource);
      SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
      
      SqlSessionFactoryProxy sqlSessionFactoryProxy = new SqlSessionFactoryProxy(sqlSessionFactory);
      
      // Dao 구현체를 만드는 공장을 준비하기
      MybatisDaoFactory daoFactory = new MybatisDaoFactory(sqlSessionFactoryProxy);
      
      context.put("lessonDao", daoFactory.createDao(LessonDao.class));
      context.put("memberDao", daoFactory.createDao(MemberDao.class));
      context.put("boardDao", daoFactory.createDao(BoardDao.class));
      context.put("photoBoardDao", daoFactory.createDao(PhotoBoardDao.class));
      context.put("photoFileDao", daoFactory.createDao(PhotoFileDao.class));
      
      context.put("transactionManager", new TransactionManager(sqlSessionFactoryProxy));
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void contextDestroyed(Map<String,Object> context) {
    System.out.println("DataLoaderListener.contextInitialized() 실행!");
  }
}
