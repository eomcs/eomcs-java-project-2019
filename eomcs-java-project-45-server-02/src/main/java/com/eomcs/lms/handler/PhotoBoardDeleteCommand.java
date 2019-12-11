package com.eomcs.lms.handler;
import java.io.BufferedReader;
import java.io.PrintWriter;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.dao.PhotoFileDao;

public class PhotoBoardDeleteCommand implements Command {

  SqlSessionFactory sqlSessionFactory;

  public PhotoBoardDeleteCommand(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void execute(BufferedReader in, PrintWriter out) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      out.print("번호?\n!{}!\n"); out.flush();
      int no = Integer.parseInt(in.readLine());
      
      PhotoBoardDao photoBoardDao = sqlSession.getMapper(PhotoBoardDao.class); 
      PhotoFileDao photoFileDao = sqlSession.getMapper(PhotoFileDao.class); 
      
      // 게시물에 첨부된 사진 파일을 먼저 삭제한다.
      photoFileDao.deleteByBoard(no);
      
      if (photoBoardDao.delete(no) > 0) {
        out.println("사진을 삭제했습니다.");
      } else { 
        out.println("해당 사진을 찾을 수 없습니다.");
      }
      sqlSession.commit();
      
    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
      
    }
  }
}
