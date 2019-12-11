package com.eomcs.lms.handler;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.domain.PhotoBoard;
import com.eomcs.lms.domain.PhotoFile;

public class PhotoBoardDetail2Command implements Command {

  SqlSessionFactory sqlSessionFactory;

  public PhotoBoardDetail2Command(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void execute(BufferedReader in, PrintWriter out) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      out.print("번호?\n!{}!\n"); out.flush();
      int no = Integer.parseInt(in.readLine());

      PhotoBoardDao photoBoardDao = sqlSession.getMapper(PhotoBoardDao.class); 
      
      PhotoBoard board = photoBoardDao.detail2(no);
      if (board == null) { 
        out.println("해당 사진을 찾을 수 없습니다.");
        return;
      }

      out.printf("제목: %s\n", board.getTitle());
      out.printf("작성일: %s\n", board.getCreatedDate());
      out.printf("조회수: %d\n", board.getViewCount());
      out.printf("수업: %d\n", board.getLessonNo());
      out.println("사진 파일:");
      
      List<PhotoFile> photos = board.getPhotos();
      
      for (PhotoFile photo : photos) {
        out.printf("> %s\n", photo.getFilepath());
      }
      
    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }
}
