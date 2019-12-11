package com.eomcs.lms.handler;
import java.io.BufferedReader;
import java.io.PrintWriter;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.domain.Board;

public class BoardUpdateCommand implements Command {

  SqlSessionFactory sqlSessionFactory;

  public BoardUpdateCommand(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void execute(BufferedReader in, PrintWriter out) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      out.print("번호?\n!{}!\n"); out.flush();
      int no = Integer.parseInt(in.readLine());

      BoardDao boardDao = sqlSession.getMapper(BoardDao.class); 
      Board board = boardDao.detail(no);
      if (board == null) { 
        out.println("해당 게시글을 찾을 수 없습니다.");
        return;
      }

      out.print("내용?\n!{}!\n"); out.flush();
      String input = in.readLine();
      if (input.length() > 0) 
        board.setContents(input);

      boardDao.update(board);

      out.println("게시글을 변경했습니다.");

      sqlSession.commit();
      
    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }
}
