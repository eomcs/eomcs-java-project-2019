package com.eomcs.lms;

import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan("com.eomcs.lms")
@PropertySource("classpath:/com/eomcs/lms/conf/jdbc.properties")
@EnableTransactionManagement  //트랜잭션 관리자를 활성화시킨다.
@MapperScan("com.eomcs.lms.dao") // DAO 인터페이스에 맞춰 프록시를 자동생성시킨다.
public class AppConfig {

  @Autowired Environment env;

  @Bean
  public DataSource dataSource() {
    BasicDataSource ds = new BasicDataSource();
    ds.setDriverClassName(env.getProperty("jdbc.driver"));
    ds.setUrl(env.getProperty("jdbc.url"));
    ds.setUsername(env.getProperty("jdbc.username"));
    ds.setPassword(env.getProperty("jdbc.password"));
    return ds;
  }

  @Bean
  public SqlSessionFactory sqlSessionFactory(DataSource dataSource, ApplicationContext appCtx) 
      throws Exception {
    SqlSessionFactoryBean factory = new SqlSessionFactoryBean();

    // DB 커넥션풀을 관리해주는 객체를 꼽는다.
    factory.setDataSource(dataSource);

    // SQL 맵퍼 파일에서 도메인 객체의 별명을 사용하려면 
    // 도메인 객체가 들어 있는 패키지를 지정해야 한다. 
    // 그러면 Mybatis가 해당 패키지의 모든 클래스에 대해 별명을 자동으로 생성할 것이다.
    factory.setTypeAliasesPackage("com.eomcs.lms.domain");

    // SQL 맵퍼 파일 경로를 등록한다.
    factory.setMapperLocations(appCtx.getResources(
        "classpath:/com/eomcs/lms/mapper/**/*.xml"));
    
    // Mybatis에서 사용하는 로깅 라이브러리를 지정한다.
    org.apache.ibatis.logging.LogFactory.useLog4JLogging();
    
    return factory.getObject();

  }

  // 트랜잭션 관리자의 이름은 반드시 "transactionManager"이어야 한다.
  // 그래서 메서드 이름을 다음과 같이 지은 것이다.
  // Spring에서 트랜잭션 관리자를 찾을 때 이 이름으로 찾는다.
  // 만약 트랜잭션 이름을 다른 이름을 지었다면 
  // 트랜잭션 관리 설정에서 그 이름을 알려줘야 한다.
  @Bean
  public PlatformTransactionManager transactionManager(DataSource dataSource) {
    // 트랜잭션 관리자가 하는 일은 DB 커넥션의 commit과 rollback을 다루는 것이다.
    // 따라서 트랜잭션 관리자는 DB 커넥션을 제공해주는 
    // DataSource(DB 커넥션 풀)가 필요하다.
    // 그래서 트랜잭션 관리자를 만들 때 반드시 DataSource 객체를 넘겨줘야 한다.
    // 물론 관리자 객체를 만든 후에 세터를 호출해서 넘겨줘도 된다.
    PlatformTransactionManager txManager = new DataSourceTransactionManager(dataSource);
    
    return txManager;
  }

}
