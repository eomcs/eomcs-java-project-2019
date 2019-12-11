package com.eomcs.lms.handler;
import java.util.Scanner;
import com.eomcs.lms.domain.Board;
import com.eomcs.lms.proxy.BoardDaoProxy;

public class BoardListCommand implements Command {
  
  Scanner keyboard;
  BoardDaoProxy boardDao;
  
  public BoardListCommand(Scanner keyboard, BoardDaoProxy boardDao) {
    this.keyboard = keyboard;
    this.boardDao = boardDao;
  }
  
  @Override
  public void execute() {
    try {
      Board[] boards = boardDao.list();
      if (boards == null) { 
        System.out.println("서버에서 데이터를 가져오는데 오류 발생!");
        return;
      }
      
      for (Board board : boards) {
        System.out.printf("%3d, %-20s, %s, %d\n", 
            board.getNo(), board.getContents(), 
            board.getCreatedDate(), board.getViewCount());
      }
      
    } catch (Exception e) {
      System.out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }

}
