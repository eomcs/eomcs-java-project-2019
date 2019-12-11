package com.eomcs.lms.handler;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.domain.PhotoBoard;
import com.eomcs.stereotype.CommandMapping;
import com.eomcs.stereotype.Component;

@Component
public class PhotoBoardListCommand {
  
  PhotoBoardDao photoBoardDao;

  public PhotoBoardListCommand(PhotoBoardDao photoBoardDao) {
    this.photoBoardDao = photoBoardDao;
  }

  @CommandMapping("/photoboard/list")
  public void list(BufferedReader in, PrintWriter out) {
    try {
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
