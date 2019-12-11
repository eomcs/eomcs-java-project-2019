package com.eomcs.lms;

import java.io.InputStream;
import java.util.Map;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.context.ApplicationContext;
import com.eomcs.context.ApplicationContextListener;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.dao.PhotoFileDao;
import com.eomcs.sql.MybatisDaoFactory;
import com.eomcs.sql.SqlSessionFactoryProxy;
import com.eomcs.sql.TransactionManager;

public class ContextLoaderListener implements ApplicationContextListener {

  @Override
  public void contextInitialized(Map<String,Object> context) {
    System.out.println("ContextLoaderListener.contextInitialized() 실행!");

    try {
      
      // IoC 컨테이너를 생성한다.
      ApplicationContext iocContainer = new ApplicationContext("com.eomcs.lms");
      
      String resource = "com/eomcs/lms/conf/mybatis-config.xml";
      InputStream inputStream = Resources.getResourceAsStream(resource);
      SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
      
      SqlSessionFactoryProxy sqlSessionFactoryProxy = new SqlSessionFactoryProxy(sqlSessionFactory);
      
      // Dao 구현체를 만드는 공장을 준비하기
      MybatisDaoFactory daoFactory = new MybatisDaoFactory(sqlSessionFactoryProxy);
      
      // 명령 핸들러가 사용할 의존 객체를 생성하여 IoC 컨테이너에 저장한다.
      iocContainer.addBean("lessonDao", daoFactory.createDao(LessonDao.class));
      iocContainer.addBean("memberDao", daoFactory.createDao(MemberDao.class));
      iocContainer.addBean("boardDao", daoFactory.createDao(BoardDao.class));
      iocContainer.addBean("photoBoardDao", daoFactory.createDao(PhotoBoardDao.class));
      iocContainer.addBean("photoFileDao", daoFactory.createDao(PhotoFileDao.class));
      iocContainer.addBean("transactionManager", new TransactionManager(sqlSessionFactoryProxy));
      
      // 명령 핸들러를 다시 준비한다.
      iocContainer.refresh();
      
      context.put("iocContainer", iocContainer);
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void contextDestroyed(Map<String,Object> context) {
    System.out.println("ContextLoaderListener.contextInitialized() 실행!");
  }
}
