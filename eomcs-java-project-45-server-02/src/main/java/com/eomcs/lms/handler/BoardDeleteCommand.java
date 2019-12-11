package com.eomcs.lms.handler;
import java.io.BufferedReader;
import java.io.PrintWriter;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.eomcs.lms.dao.BoardDao;

public class BoardDeleteCommand implements Command {

  SqlSessionFactory sqlSessionFactory;

  public BoardDeleteCommand(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void execute(BufferedReader in, PrintWriter out) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      out.print("번호?\n!{}!\n"); out.flush();
      int no = Integer.parseInt(in.readLine());

      BoardDao boardDao = sqlSession.getMapper(BoardDao.class); 
      if (boardDao.delete(no) > 0) 
        out.println("게시글을 삭제했습니다.");
      else 
        out.println("해당 게시글을 찾을 수 없습니다.");

      sqlSession.commit();
      
    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }
}
