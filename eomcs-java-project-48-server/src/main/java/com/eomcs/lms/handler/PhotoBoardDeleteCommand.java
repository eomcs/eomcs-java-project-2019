package com.eomcs.lms.handler;
import java.io.BufferedReader;
import java.io.PrintWriter;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.dao.PhotoFileDao;
import com.eomcs.sql.TransactionManager;
import com.eomcs.stereotype.CommandMapping;
import com.eomcs.stereotype.Component;

@Component
public class PhotoBoardDeleteCommand {

  PhotoBoardDao photoBoardDao;
  PhotoFileDao photoFileDao;
  TransactionManager txManager;
  
  public PhotoBoardDeleteCommand(PhotoBoardDao photoBoardDao, PhotoFileDao photoFileDao, TransactionManager txManager) {
    this.photoBoardDao = photoBoardDao;
    this.photoFileDao = photoFileDao;
    this.txManager = txManager;
  }

  @CommandMapping("/photoboard/delete")
  public void delete(BufferedReader in, PrintWriter out) {
    try {
      out.print("번호?\n!{}!\n"); out.flush();
      int no = Integer.parseInt(in.readLine());
      
      txManager.beginTransaction();
      
      // 게시물에 첨부된 사진 파일을 먼저 삭제한다.
      photoFileDao.deleteByBoard(no);
      
      if (photoBoardDao.delete(no) > 0) {
        out.println("사진을 삭제했습니다.");
      } else { 
        out.println("해당 사진을 찾을 수 없습니다.");
      }
      txManager.commit();
      
    } catch (Exception e) {
      txManager.rollback();
      out.printf("%s : %s\n", e.toString(), e.getMessage());
      
    }
  }
}
