package com.eomcs.sql;

import java.sql.Connection;

public class TransactionManager {

  DataSource dataSource;

  public TransactionManager(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public void beginTransaction() throws Exception {
    System.out.println("트랜잭션을 시작함.");
    dataSource.beginTransaction();
  }

  public void commit() throws Exception {
    Connection con = dataSource.getConnection();
    con.commit();
    dataSource.endTransaction();
    System.out.println("commit()");
  }

  public void rollback() {
    try {
      Connection con = dataSource.getConnection();
      con.rollback();
      dataSource.endTransaction();
      System.out.println("rollback()");
    } catch (Exception e) {
      // SQL 실행을 되돌리다가 오류가 발생할 때는 무시한다.
    }
  }
}
