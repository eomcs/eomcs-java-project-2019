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
      return count;
    } finally {
      session.close();
    }
  }

  public int deleteByBoard(int boardNo) throws Exception {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      int count = session.delete("PhotoFileMapper.deleteByBoard", boardNo);
      return count;
    } finally {
      session.close();
    }
  }

  public int add(PhotoFile photoFile) throws Exception {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      int count = session.insert("PhotoFileMapper.add", photoFile);
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
