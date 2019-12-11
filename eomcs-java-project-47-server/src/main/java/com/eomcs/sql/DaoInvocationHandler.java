package com.eomcs.sql;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class DaoInvocationHandler implements InvocationHandler {

  SqlSessionFactory sqlSessionFactory;
  
  public DaoInvocationHandler(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }
  
  
  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    SqlSession session = sqlSessionFactory.openSession();
    
    try {
      String sqlId = proxy.getClass().getInterfaces()[0].getSimpleName() + "." + method.getName();
      Class<?> retType = method.getReturnType();
      
      if (retType == int.class)
        return (args == null) ? session.update(sqlId): session.update(sqlId, args[0]);
      else if (retType == List.class)
        return (args == null) ? session.selectList(sqlId): session.selectList(sqlId, args[0]);
      else
        return (args == null) ? session.selectOne(sqlId): session.selectOne(sqlId, args[0]);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      session.close();
    }
  }
}
