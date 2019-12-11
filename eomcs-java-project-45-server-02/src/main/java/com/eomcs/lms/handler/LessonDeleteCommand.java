package com.eomcs.lms.handler;
import java.io.BufferedReader;
import java.io.PrintWriter;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.eomcs.lms.dao.LessonDao;

public class LessonDeleteCommand implements Command {

  SqlSessionFactory sqlSessionFactory;

  public LessonDeleteCommand(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void execute(BufferedReader in, PrintWriter out) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      out.print("번호?\n!{}!\n"); out.flush();
      int no = Integer.parseInt(in.readLine());

      LessonDao lessonDao = sqlSession.getMapper(LessonDao.class); 
      if (lessonDao.delete(no) > 0) 
        out.println("수업을 삭제했습니다.");
      else 
        out.println("해당 수업을 찾을 수 없습니다.");

      sqlSession.commit();
      
    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }
}
