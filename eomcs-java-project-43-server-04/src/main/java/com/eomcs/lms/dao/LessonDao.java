package com.eomcs.lms.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.eomcs.lms.domain.Lesson;

public class LessonDao {

  SqlSessionFactory sqlSessionFactory;

  public LessonDao(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  public int delete(int no) throws Exception {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      int count = session.delete("LessonMapper.delete", no);
      session.commit(); // 기본이 수동 커밋 상태이기 때문에 작업이 끝난 후 commit 명령을 내려야 한다.
      return count;
    } finally {
      session.close();
    }
  }

  public int update(Lesson lesson) throws Exception {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      int count = session.update("LessonMapper.update", lesson);
      session.commit(); // 기본이 수동 커밋 상태이기 때문에 작업이 끝난 후 commit 명령을 내려야 한다.
      return count;
    } finally {
      session.close();
    }
  }

  public int add(Lesson lesson) throws Exception {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      int count = session.insert("LessonMapper.add", lesson);
      session.commit(); // 기본이 수동 커밋 상태이기 때문에 작업이 끝난 후 commit 명령을 내려야 한다.
      return count;
    } finally {
      session.close();
    }
  }

  public List<Lesson> list() throws Exception {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      return session.selectList("LessonMapper.list");
    } finally {
      session.close();
    }
  }

  public Lesson detail(int no) throws Exception {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      return session.selectOne("LessonMapper.detail", no);
    } finally {
      session.close();
    }

  }
}
