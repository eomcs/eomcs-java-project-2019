package com.eomcs.lms.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.eomcs.lms.domain.PhotoBoard;

public class PhotoBoardDao {

  SqlSessionFactory sqlSessionFactory;

  public PhotoBoardDao(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  public int delete(int no) throws Exception {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      int count = session.delete("PhotoBoardMapper.delete", no);
      return count;
    } finally {
      session.close();
    }
  }

  public int update(PhotoBoard photo) throws Exception {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      int count = session.update("PhotoBoardMapper.update", photo);
      return count;
    } finally {
      session.close();
    }
  }

  public int add(PhotoBoard photo) throws Exception {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      int count = session.insert("PhotoBoardMapper.add", photo);
      return count;
    } finally {
      session.close();
    }
  }

  public List<PhotoBoard> list() throws Exception {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      return session.selectList("PhotoBoardMapper.list");
    } finally {
      session.close();
    }
  }

  public PhotoBoard detail(int no) throws Exception {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      return session.selectOne("PhotoBoardMapper.detail", no);
    } finally {
      session.close();
    }
  }
  
  public PhotoBoard detail2(int no) throws Exception {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      return session.selectOne("PhotoBoardMapper.detail2", no);
    } finally {
      session.close();
    }
  }
}
