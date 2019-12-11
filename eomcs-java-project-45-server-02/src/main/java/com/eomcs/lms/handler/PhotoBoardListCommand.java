package com.eomcs.lms.handler;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.domain.PhotoBoard;

public class PhotoBoardListCommand implements Command {
  
  SqlSessionFactory sqlSessionFactory;

  public PhotoBoardListCommand(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void execute(BufferedReader in, PrintWriter out) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      
      PhotoBoardDao photoBoardDao = sqlSession.getMapper(PhotoBoardDao.class); 
      
      List<PhotoBoard> boards = photoBoardDao.list();
      if (boards == null) { 
        out.println("서버에서 데이터를 가져오는데 오류 발생!");
        return;
      }
      
      for (PhotoBoard board : boards) {
        out.printf("%3d, %-20s, %s, %d, %d\n", 
            board.getNo(), board.getTitle(), 
            board.getCreatedDate(), board.getViewCount(), board.getLessonNo());
      }
      
    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }

}
