package com.eomcs.sql;

import java.sql.Connection;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.TransactionIsolationLevel;

public class SqlSessionFactoryProxy implements SqlSessionFactory {

    SqlSessionFactory original;
    
    ThreadLocal<SqlSessionProxy> sqlSessionProxyLocal = new ThreadLocal<>();
    
    public SqlSessionFactoryProxy(SqlSessionFactory original) {
      this.original = original;
    }

    public void beginTransaction() throws Exception {
      if (sqlSessionProxyLocal.get() != null) {
        throw new Exception("이미 트랜잭션이 시작되었음.");
      }
      
      // 트랜재션을 시작하면 미리 SqlSessionProxy 객체를 만들어 스레드 로컬 변수에 담아 둔다.
      SqlSessionProxy sqlSessionProxy = new SqlSessionProxy(this.openSession(false), true);
      sqlSessionProxyLocal.set(sqlSessionProxy);
    }
    
    public void endTransaction() throws Exception {
      
      // 트랜잭션을 종료하면 스레드 로컬에서 값을 꺼낸다.
      SqlSessionProxy sqlSessionProxy = sqlSessionProxyLocal.get(); 
      
      // 스레드 로컬에 들어있는 값이 없다면 트랜잭션 상태가 아니기에 그냥 리턴한다.
      if (sqlSessionProxy == null)
        return; 
      
      // 스레드 로컬에 SqlSessionProxy 객체가 들어 있다면 자원을 해제시킨다.
      sqlSessionProxy.close();
      
      // 스레드 로컬에 들어 있는 객체를 제거한다.
      sqlSessionProxyLocal.remove();
    }
    
    
    public SqlSession openSession() {
      
      SqlSessionProxy sqlSessionProxy = sqlSessionProxyLocal.get();
      
      // 스레드 로컬에 SqlSessionProxy 객체가 들어 있으면 그 객체를 리턴한다.
      if (sqlSessionProxy != null)
        return sqlSessionProxy;
      else // 없으면 트랜잭션 상태가 아니므로 일반적인 용도의 객체를 리턴한다. 자동 커밋으로 동작한다.
        return original.openSession(true);
    }

    public SqlSession openSession(boolean autoCommit) {
      return original.openSession(autoCommit);
    }

    public SqlSession openSession(Connection connection) {
      return original.openSession(connection);
    }

    public SqlSession openSession(TransactionIsolationLevel level) {
      return original.openSession(level);
    }

    public SqlSession openSession(ExecutorType execType) {
      return original.openSession(execType);
    }

    public SqlSession openSession(ExecutorType execType, boolean autoCommit) {
      return original.openSession(execType, autoCommit);
    }

    public SqlSession openSession(ExecutorType execType, TransactionIsolationLevel level) {
      return original.openSession(execType, level);
    }

    public SqlSession openSession(ExecutorType execType, Connection connection) {
      return original.openSession(execType, connection);
    }

    public Configuration getConfiguration() {
      return original.getConfiguration();
    }
    
    
}
