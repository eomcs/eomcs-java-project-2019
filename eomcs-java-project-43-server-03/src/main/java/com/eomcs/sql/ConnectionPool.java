package com.eomcs.sql;

import java.sql.Connection;

public interface ConnectionPool {
  Connection getConnection() throws Exception;
  void returnConnection(Connection connection);
  void close();
}
