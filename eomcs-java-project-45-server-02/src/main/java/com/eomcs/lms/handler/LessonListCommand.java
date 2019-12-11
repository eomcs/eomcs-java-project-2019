package com.eomcs.lms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.domain.Lesson;

public class LessonListCommand implements Command {
  
  SqlSessionFactory sqlSessionFactory;

  public LessonListCommand(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void execute(BufferedReader in, PrintWriter out) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      LessonDao lessonDao = sqlSession.getMapper(LessonDao.class); 
      List<Lesson> lessons = lessonDao.list(null);
      if (lessons == null) { 
        out.println("서버에서 데이터를 가져오는데 오류 발생!");
        return;
      }
      
      for (Lesson lesson : lessons) {
        out.printf("%3d, %-15s, %10s ~ %10s, %4d\n", 
            lesson.getNo(), lesson.getTitle(), 
            lesson.getStartDate(), lesson.getEndDate(), lesson.getTotalHours());
      }
      
    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }
}
