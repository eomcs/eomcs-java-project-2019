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
      session.commit(); // 기본이 수동 커밋 상태이기 때문에 작업이 끝난 후 commit 명령을 내려야 한다.
      return count;
    } finally {
      session.close();
    }
  }

  public int update(Board board) throws Exception {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      int count = session.update("BoardMapper.update", board);
      session.commit(); // 기본이 수동 커밋 상태이기 때문에 작업이 끝난 후 commit 명령을 내려야 한다.
      return count;
    } finally {
      session.close();
    }
  }

  public int add(Board board) throws Exception {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      int count = session.insert("BoardMapper.add", board);
      session.commit(); // 기본이 수동 커밋 상태이기 때문에 작업이 끝난 후 commit 명령을 내려야 한다.
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
