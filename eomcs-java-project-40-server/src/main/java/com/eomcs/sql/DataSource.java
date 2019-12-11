package com.eomcs.sql;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataSource {
  
  String jdbcUrl;
  String username;
  String password;
  
  ThreadLocal<Connection> connectionLocal = new ThreadLocal<>();
  
  public DataSource(String jdbcUrl, String username, String password) {
    this.jdbcUrl = jdbcUrl;
    this.username = username;
    this.password = password;
  }
  
  public Connection getConnection() throws Exception {
    Connection connection = null;
    
    // 트랜잭션에 소속된 작업이 아닌 경우 DB 커넥션을 새로 만들어 리턴한다.
    if (connectionLocal.get() == null) {
      System.out.printf("[%s] - DB 커넥션 생성\n", Thread.currentThread().getName());
      connection = new TxConnection(DriverManager.getConnection(jdbcUrl, username, password));
      
    } else { // 트랜잭션에 소속된 작업의 경우 ThreadLocal에 저장된 커넥션을 꺼내 리턴한다.
      System.out.printf("[%s] - ThreadLocal의 DB 커넥션 리턴\n", Thread.currentThread().getName());
      connection = connectionLocal.get();
    }
    
    return connection;
  }
  
  // 트랜잭션을 시작하면 DB 커넥션을 꺼내기 전에 미리 생성하여 ThreadLocal 변수에 보관한다. 
  public void beginTransaction() throws Exception {
    
    if (connectionLocal.get() != null) {
      throw new Exception("이미 트랜잭션이 시작되었음.");
    }

    TxConnection con = (TxConnection) getConnection();
    con.setInTransaction(true);
    con.setAutoCommit(false);
    
    connectionLocal.set(con);
    System.out.printf("[%s] - ThreadLocal <=== DB 커넥션 보관\n", Thread.currentThread().getName());
  }
  
  // 트랜잭션을 종료하면 ThreadLocal에 보관된 DB 커넥션 객체를 제거한다.
  public void endTransaction() {
    System.out.printf("[%s] - ThreadLocal ===> DB 커넥션 제거\n", Thread.currentThread().getName());
    
    Connection con = connectionLocal.get();
    try {con.close();} catch (Exception e) {}
    
    connectionLocal.remove();
  }
  
  public static void main(String[] args) throws Exception {
    DataSource ds = new DataSource("jdbc:mariadb://localhost:3306/eomcs",
        "eomcs", "1111");
    
    ds.beginTransaction();
    Connection c1 = ds.getConnection();
    Connection c2 = ds.getConnection();
    
    
    if (c1 == c2) {
      System.out.println("c1 == c2");
    } else {
      System.out.println("c1 != c2");
    }
    
    ds.endTransaction();
    
    try {c1.close();} catch (Exception e) {}
    try {c2.close();} catch (Exception e) {}
    
    c1 = ds.getConnection();
    c2 = ds.getConnection();
    
    
    if (c1 == c2) {
      System.out.println("c1 == c2");
    } else {
      System.out.println("c1 != c2");
    }
    try {c1.close();} catch (Exception e) {}
    try {c2.close();} catch (Exception e) {}
  }
}


