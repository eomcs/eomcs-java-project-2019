package com.eomcs.lms.handler;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.domain.Board;

public class BoardListCommand implements Command {
  
  SqlSessionFactory sqlSessionFactory;

  public BoardListCommand(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void execute(BufferedReader in, PrintWriter out) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      
      BoardDao boardDao = sqlSession.getMapper(BoardDao.class); 
      List<Board> boards = boardDao.list();
      if (boards == null) { 
        out.println("서버에서 데이터를 가져오는데 오류 발생!");
        return;
      }
      
      for (Board board : boards) {
        out.printf("%3d, %-20s, %s, %d, %d, %d\n", 
            board.getNo(), board.getContents(), 
            board.getCreatedDate(), board.getViewCount(), 
            board.getWriterNo(), board.getLessonNo());
      }
      
    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }

}
