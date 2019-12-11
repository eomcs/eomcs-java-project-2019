package com.eomcs.sql;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

public class SqlSessionProxy implements SqlSession {
  
  boolean isInTransaction;
  SqlSession original;
  
  public SqlSessionProxy(SqlSession original) {
    this(original, false);
  }
  
  public SqlSessionProxy(SqlSession original, boolean isInTransaction) {
    this.original = original;
    this.isInTransaction = isInTransaction;
  }

  public <T> T selectOne(String statement) {
    return original.selectOne(statement);
  }

  public <T> T selectOne(String statement, Object parameter) {
    return original.selectOne(statement, parameter);
  }

  public <E> List<E> selectList(String statement) {
    return original.selectList(statement);
  }

  public <E> List<E> selectList(String statement, Object parameter) {
    return original.selectList(statement, parameter);
  }

  public <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds) {
    return original.selectList(statement, parameter, rowBounds);
  }

  public <K, V> Map<K, V> selectMap(String statement, String mapKey) {
    return original.selectMap(statement, mapKey);
  }

  public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey) {
    return original.selectMap(statement, parameter, mapKey);
  }

  public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey,
      RowBounds rowBounds) {
    return original.selectMap(statement, parameter, mapKey, rowBounds);
  }

  public <T> Cursor<T> selectCursor(String statement) {
    return original.selectCursor(statement);
  }

  public <T> Cursor<T> selectCursor(String statement, Object parameter) {
    return original.selectCursor(statement, parameter);
  }

  public <T> Cursor<T> selectCursor(String statement, Object parameter, RowBounds rowBounds) {
    return original.selectCursor(statement, parameter, rowBounds);
  }

  @SuppressWarnings("rawtypes")
  public void select(String statement, Object parameter, ResultHandler handler) {
    original.select(statement, parameter, handler);
  }

  @SuppressWarnings("rawtypes")
  public void select(String statement, ResultHandler handler) {
    original.select(statement, handler);
  }

  @SuppressWarnings("rawtypes")
  public void select(String statement, Object parameter, RowBounds rowBounds,
      ResultHandler handler) {
    original.select(statement, parameter, rowBounds, handler);
  }

  public int insert(String statement) {
    return original.insert(statement);
  }

  public int insert(String statement, Object parameter) {
    return original.insert(statement, parameter);
  }

  public int update(String statement) {
    return original.update(statement);
  }

  public int update(String statement, Object parameter) {
    return original.update(statement, parameter);
  }

  public int delete(String statement) {
    return original.delete(statement);
  }

  public int delete(String statement, Object parameter) {
    return original.delete(statement, parameter);
  }

  public void commit() {
    original.commit();
    this.isInTransaction = false;
  }

  public void commit(boolean force) {
    original.commit(force);
    this.isInTransaction = false;
  }

  public void rollback() {
    original.rollback();
    this.isInTransaction = false;
  }

  public void rollback(boolean force) {
    original.rollback(force);
    this.isInTransaction = false;
  }

  public List<BatchResult> flushStatements() {
    return original.flushStatements();
  }

  public void close() {
    if (isInTransaction)
      return;
    
    original.close();
  }

  public void clearCache() {
    original.clearCache();
  }

  public Configuration getConfiguration() {
    return original.getConfiguration();
  }

  public <T> T getMapper(Class<T> type) {
    return original.getMapper(type);
  }

  public Connection getConnection() {
    return original.getConnection();
  }
  
  
}
