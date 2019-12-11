package com.eomcs.lms.handler;
import java.io.BufferedReader;
import java.io.PrintWriter;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.domain.PhotoBoard;

public class PhotoBoardUpdateCommand implements Command {

  PhotoBoardDao photoBoardDao;

  public PhotoBoardUpdateCommand(PhotoBoardDao photoBoardDao) {
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

      out.printf("제목(%s)?\n!{}!\n", board.getTitle()); out.flush();
      String input = in.readLine();
      if (input.length() > 0) 
        board.setTitle(input);

      photoBoardDao.update(board);

      out.println("사진을 변경했습니다.");

    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }
}
