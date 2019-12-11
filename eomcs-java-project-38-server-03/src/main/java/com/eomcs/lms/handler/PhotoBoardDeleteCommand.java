package com.eomcs.lms.handler;
import java.io.BufferedReader;
import java.io.PrintWriter;
import com.eomcs.lms.DataLoaderListener;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.dao.PhotoFileDao;

public class PhotoBoardDeleteCommand implements Command {

  PhotoBoardDao photoBoardDao;
  PhotoFileDao photoFileDao;
  
  public PhotoBoardDeleteCommand(PhotoBoardDao photoBoardDao, PhotoFileDao photoFileDao) {
    this.photoBoardDao = photoBoardDao;
    this.photoFileDao = photoFileDao;
  }

  @Override
  public void execute(BufferedReader in, PrintWriter out) {
    try {
      DataLoaderListener.connection.setAutoCommit(false);
      out.print("번호?\n!{}!\n"); out.flush();
      int no = Integer.parseInt(in.readLine());

      // 게시물에 첨부된 사진 파일을 먼저 삭제한다.
      photoFileDao.deleteByBoard(no);
      
      if (photoBoardDao.delete(no) > 0) {
        out.println("사진을 삭제했습니다.");
      } else { 
        out.println("해당 사진을 찾을 수 없습니다.");
      }
      DataLoaderListener.connection.commit();
      
    } catch (Exception e) {
      try {DataLoaderListener.connection.rollback();} catch (Exception ex) {}
      out.printf("%s : %s\n", e.toString(), e.getMessage());
      
    } finally {
      try {DataLoaderListener.connection.setAutoCommit(true);} catch (Exception ex) {}
    }
  }
}
