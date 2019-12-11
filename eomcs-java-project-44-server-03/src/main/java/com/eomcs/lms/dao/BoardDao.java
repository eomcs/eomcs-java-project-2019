package com.eomcs.lms.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.eomcs.lms.domain.Board;

public class BoardDao {

  SqlSessionFactory sqlSessionFactory;

  public BoardDao(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  public int delete(int no) throws Exception {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      int count = session.delete("BoardMapper.delete", no);
      return count;
    } finally {
      session.close();
    }
  }

  public int update(Board board) throws Exception {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      int count = session.update("BoardMapper.update", board);
      return count;
    } finally {
      session.close();
    }
  }

  public int add(Board board) throws Exception {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      int count = session.insert("BoardMapper.add", board);
      return count;
    } finally {
      session.close();
    }
  }

  public List<Board> list() throws Exception {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      return session.selectList("BoardMapper.list");
    } finally {
      session.close();
    }
  }

  public Board detail(int no) throws Exception {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      return session.selectOne("BoardMapper.detail", no);
    } finally {
      session.close();
    }
  }
}
