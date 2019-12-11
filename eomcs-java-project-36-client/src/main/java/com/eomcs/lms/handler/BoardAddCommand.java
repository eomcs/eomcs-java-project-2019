package com.eomcs.lms.handler;
import java.util.Scanner;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.domain.Board;

public class BoardAddCommand implements Command {
  
  Scanner keyboard;
  BoardDao boardDao;
  
  public BoardAddCommand(Scanner keyboard, BoardDao boardDao) {
    this.keyboard = keyboard;
    this.boardDao = boardDao;
  }
  
  @Override
  public void execute() {
    Board board = new Board();
    
    System.out.print("내용? ");
    board.setContents(keyboard.nextLine());
    
    System.out.print("작성자? ");
    board.setWriterNo(Integer.parseInt(keyboard.nextLine()));
    
    System.out.print("수업? ");
    board.setLessonNo(Integer.parseInt(keyboard.nextLine()));
    
    try {
      boardDao.add(board);
      System.out.println("게시글을 저장했습니다.");
      
    } catch (Exception e) {
      System.out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
    
  }
}
