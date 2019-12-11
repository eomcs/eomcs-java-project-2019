package com.eomcs.lms.handler;
import java.io.BufferedReader;
import java.io.PrintWriter;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.domain.PhotoBoard;

public class PhotoBoardDetailCommand implements Command {

  PhotoBoardDao photoBoardDao;

  public PhotoBoardDetailCommand(PhotoBoardDao photoBoardDao) {
    this.photoBoardDao = photoBoardDao;
  }

  @Override
  public void execute(BufferedReader in, PrintWriter out) {
    try {
      out.print("번호?\n!{}!\n"); out.flush();
      int no = Integer.parseInt(in.readLine());

      PhotoBoard board = photoBoardDao.detail(no);
      if (board == null) { 
        out.println("해당 사진을 찾을 수 없습니다.");
        return;
      }

      out.printf("제목: %s\n", board.getTitle());
      out.printf("작성일: %s\n", board.getCreatedDate());
      out.printf("조회수: %d\n", board.getViewCount());
      out.printf("수업: %d\n", board.getLessonNo());

    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }
}
