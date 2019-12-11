package com.eomcs.lms.handler;
import java.io.BufferedReader;
import java.io.PrintWriter;
import com.eomcs.lms.dao.PhotoBoardDao;

public class PhotoBoardDeleteCommand implements Command {

  PhotoBoardDao photoBoardDao;

  public PhotoBoardDeleteCommand(PhotoBoardDao photoBoardDao) {
    this.photoBoardDao = photoBoardDao;
  }

  @Override
  public void execute(BufferedReader in, PrintWriter out) {
    try {
      out.print("번호?\n!{}!\n"); out.flush();
      int no = Integer.parseInt(in.readLine());

      if (photoBoardDao.delete(no) > 0) 
        out.println("사진을 삭제했습니다.");
      else 
        out.println("해당 사진을 찾을 수 없습니다.");

    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }
}
