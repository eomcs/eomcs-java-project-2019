package com.eomcs.lms.handler;
import java.io.BufferedReader;
import java.io.PrintWriter;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.stereotype.CommandMapping;
import com.eomcs.stereotype.Component;

@Component
public class BoardDeleteCommand {

  BoardDao boardDao;

  public BoardDeleteCommand(BoardDao boardDao) {
    this.boardDao = boardDao;
  }

  @CommandMapping("/board/delete")
  public void delete(BufferedReader in, PrintWriter out) {
    try {
      out.print("번호?\n!{}!\n"); out.flush();
      int no = Integer.parseInt(in.readLine());

      if (boardDao.delete(no) > 0) 
        out.println("게시글을 삭제했습니다.");
      else 
        out.println("해당 게시글을 찾을 수 없습니다.");

    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }
}
