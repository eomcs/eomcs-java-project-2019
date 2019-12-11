package com.eomcs.lms.handler;
import java.util.List;
import java.util.Scanner;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.domain.Board;

public class BoardListCommand implements Command {
  
  Scanner keyboard;
  BoardDao boardDao;
  
  public BoardListCommand(Scanner keyboard, BoardDao boardDao) {
    this.keyboard = keyboard;
    this.boardDao = boardDao;
  }
  
  @Override
  public void execute() {
    try {
      List<Board> boards = boardDao.list();
      if (boards == null) { 
        System.out.println("서버에서 데이터를 가져오는데 오류 발생!");
        return;
      }
      
      for (Board board : boards) {
        System.out.printf("%3d, %-20s, %s, %d, %d, %d\n", 
            board.getNo(), board.getContents(), 
            board.getCreatedDate(), board.getViewCount(), 
            board.getWriterNo(), board.getLessonNo());
      }
      
    } catch (Exception e) {
      System.out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }

}
