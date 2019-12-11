package com.eomcs.lms.handler;
import java.io.BufferedReader;
import java.io.PrintWriter;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.domain.Board;
import com.eomcs.stereotype.CommandMapping;
import com.eomcs.stereotype.Component;

@Component
public class BoardAddCommand {

  BoardDao boardDao;

  public BoardAddCommand(BoardDao boardDao) {
    this.boardDao = boardDao;
  }

  @CommandMapping("/board/add")
  public void add(BufferedReader in, PrintWriter out) {
    try {
      Board board = new Board();

      out.print("내용?\n!{}!\n"); out.flush();
      board.setContents(in.readLine());

      out.print("작성자?\n!{}!\n"); out.flush();
      board.setWriterNo(Integer.parseInt(in.readLine()));

      out.print("수업?\n!{}!\n"); out.flush();
      board.setLessonNo(Integer.parseInt(in.readLine()));

      boardDao.add(board);
      out.println("게시글을 저장했습니다.");

    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }

  }
}
