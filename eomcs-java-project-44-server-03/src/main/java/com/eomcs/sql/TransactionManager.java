package com.eomcs.sql;

public class TransactionManager {

  SqlSessionFactoryProxy sqlSessionFactoryProxy;

  public TransactionManager(SqlSessionFactoryProxy sqlSessionFactoryProxy) {
    this.sqlSessionFactoryProxy = sqlSessionFactoryProxy;
  }

  public void beginTransaction() throws Exception {
    System.out.println("트랜잭션을 시작함.");
    sqlSessionFactoryProxy.beginTransaction();
  }

  public void commit() throws Exception {
    SqlSessionProxy sqlSession = (SqlSessionProxy)sqlSessionFactoryProxy.openSession();
    sqlSession.commit();
    System.out.println("commit()");
    sqlSessionFactoryProxy.endTransaction();
  }

  public void rollback() {
    try {
      SqlSessionProxy sqlSession = (SqlSessionProxy)sqlSessionFactoryProxy.openSession();
      sqlSession.rollback();
      System.out.println("rollback()");
      sqlSessionFactoryProxy.endTransaction();
    } catch (Exception e) {
      // SQL 실행을 되돌리다가 오류가 발생할 때는 무시한다.
    }
  }
}
