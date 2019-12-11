package com.eomcs.sql;

import java.lang.reflect.Proxy;
import org.apache.ibatis.session.SqlSessionFactory;

public class MybatisDaoFactory {
  SqlSessionFactory sqlSessionFactory;
  
  public MybatisDaoFactory(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }
  
  @SuppressWarnings("unchecked")
  public <T> T createDao(Class<T> clazz) {
    return (T) Proxy.newProxyInstance(
            // 클래스 로더 
            clazz.getClassLoader(),
            
            // 인터페이스들의 타입 정보
            new Class[] {clazz}, 
            
            // 프록시의 메서드를 호출할 때 마다 실행될 필터 객체 
            new DaoInvocationHandler(sqlSessionFactory));
  }
}
