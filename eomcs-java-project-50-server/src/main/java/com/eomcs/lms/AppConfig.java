package com.eomcs.lms;

import java.io.InputStream;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.dao.PhotoFileDao;
import com.eomcs.sql.MybatisDaoFactory;
import com.eomcs.sql.SqlSessionFactoryProxy;
import com.eomcs.sql.TransactionManager;

@ComponentScan("com.eomcs.lms")
public class AppConfig {
  
  @Bean
  public SqlSessionFactory sqlSessionFactory() throws Exception {
    String resource = "com/eomcs/lms/conf/mybatis-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    
    return new SqlSessionFactoryProxy(sqlSessionFactory);
  }
  
  @Bean
  public MybatisDaoFactory mybatisDaoFactory(SqlSessionFactory sqlSessionFactory) {
    // Dao 구현체를 만드는 공장을 준비하기
    return new MybatisDaoFactory(sqlSessionFactory);
  }
  
  @Bean
  public LessonDao lessonDao(MybatisDaoFactory daoFactory) {
    return daoFactory.createDao(LessonDao.class);
  }
  
  @Bean
  public MemberDao memberDao(MybatisDaoFactory daoFactory) {
    return daoFactory.createDao(MemberDao.class);
  }
  
  @Bean
  public BoardDao boardDao(MybatisDaoFactory daoFactory) {
    return daoFactory.createDao(BoardDao.class);
  }
  
  @Bean
  public PhotoBoardDao photoBoardDao(MybatisDaoFactory daoFactory) {
    return daoFactory.createDao(PhotoBoardDao.class);
  }
  
  @Bean
  public PhotoFileDao photoFileDao(MybatisDaoFactory daoFactory) {
    return daoFactory.createDao(PhotoFileDao.class);
  }
  
  @Bean
  public TransactionManager transactionManager(SqlSessionFactory sqlSessionFactory) {
    return new TransactionManager((SqlSessionFactoryProxy)sqlSessionFactory);
  }
}
