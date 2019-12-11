package com.eomcs.sql;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataSource {
  
  String jdbcUrl;
  String username;
  String password;
  
  public DataSource(String jdbcUrl, String username, String password) {
    this.jdbcUrl = jdbcUrl;
    this.username = username;
    this.password = password;
  }
  
  public Connection getConnection() throws Exception {
    System.out.println("새 DB 커넥션을 생성한다.");
    return DriverManager.getConnection(jdbcUrl, username, password);
  }
}
