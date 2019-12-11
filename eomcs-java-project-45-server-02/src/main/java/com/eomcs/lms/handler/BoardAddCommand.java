package com.eomcs.lms.handler;
import java.io.BufferedReader;
import java.io.PrintWriter;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.domain.Board;

public class BoardAddCommand implements Command {

  SqlSessionFactory sqlSessionFactory;

  public BoardAddCommand(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void execute(BufferedReader in, PrintWriter out) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Board board = new Board();

      out.print("내용?\n!{}!\n"); out.flush();
      board.setContents(in.readLine());

      out.print("작성자?\n!{}!\n"); out.flush();
      board.setWriterNo(Integer.parseInt(in.readLine()));

      out.print("수업?\n!{}!\n"); out.flush();
      board.setLessonNo(Integer.parseInt(in.readLine()));

      BoardDao boardDao = sqlSession.getMapper(BoardDao.class); 
      boardDao.add(board);
      out.println("게시글을 저장했습니다.");

      sqlSession.commit();
      
    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }

  }
}
