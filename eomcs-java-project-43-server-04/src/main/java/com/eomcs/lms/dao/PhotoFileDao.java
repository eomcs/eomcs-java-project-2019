package com.eomcs.lms.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.eomcs.lms.domain.PhotoFile;

public class PhotoFileDao {

  SqlSessionFactory sqlSessionFactory;

  public PhotoFileDao(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  public int delete(int no) throws Exception {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      int count = session.delete("PhotoFileMapper.delete", no);
      session.commit(); // 기본이 수동 커밋 상태이기 때문에 작업이 끝난 후 commit 명령을 내려야 한다.
      return count;
    } finally {
      session.close();
    }
  }

  public int deleteByBoard(int boardNo) throws Exception {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      int count = session.delete("PhotoFileMapper.deleteByBoard", boardNo);
      session.commit(); // 기본이 수동 커밋 상태이기 때문에 작업이 끝난 후 commit 명령을 내려야 한다.
      return count;
    } finally {
      session.close();
    }
  }

  public int add(PhotoFile photoFile) throws Exception {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      int count = session.insert("PhotoFileMapper.add", photoFile);
      session.commit(); // 기본이 수동 커밋 상태이기 때문에 작업이 끝난 후 commit 명령을 내려야 한다.
      return count;
    } finally {
      session.close();
    }
  }

  public List<PhotoFile> list() throws Exception {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      return session.selectList("PhotoFileMapper.list");
    } finally {
      session.close();
    }
  }

  public List<PhotoFile> listByBoard(int boardNo) throws Exception {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      return session.selectList("PhotoFileMapper.listByBoard", boardNo);
    } finally {
      session.close();
    }
  }
}
