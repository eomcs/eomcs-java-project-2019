package com.eomcs.lms.handler;
import java.io.BufferedReader;
import java.io.PrintWriter;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.domain.Board;

public class BoardDetailCommand implements Command {

  public final String name = "/board/detail";
  
  BoardDao boardDao;

  public BoardDetailCommand(BoardDao boardDao) {
    this.boardDao = boardDao;
  }

  @Override
  public void execute(BufferedReader in, PrintWriter out) {
    try {
      out.print("번호?\n!{}!\n"); out.flush();
      int no = Integer.parseInt(in.readLine());

      Board board = boardDao.detail(no);
      if (board == null) { 
        out.println("해당 게시글을 찾을 수 없습니다.");
        return;
      }

      out.printf("내용: %s\n", board.getContents());
      out.printf("작성일: %s\n", board.getCreatedDate());
      out.printf("조회수: %d\n", board.getViewCount());
      out.printf("작성자: %d\n", board.getWriterNo());
      out.printf("수업: %d\n", board.getLessonNo());

    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }
}
