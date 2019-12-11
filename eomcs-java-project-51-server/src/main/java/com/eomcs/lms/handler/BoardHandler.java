package com.eomcs.lms.handler;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;
import org.springframework.stereotype.Component;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.domain.Board;
import com.eomcs.stereotype.CommandMapping;

@Component
public class BoardHandler {

  BoardDao boardDao;

  public BoardHandler(BoardDao boardDao) {
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
  
  @CommandMapping("/board/detail")
  public void detail(BufferedReader in, PrintWriter out) {
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
  
  @CommandMapping("/board/list")
  public void list(BufferedReader in, PrintWriter out) {
    try {
      List<Board> boards = boardDao.list();
      if (boards == null) { 
        out.println("서버에서 데이터를 가져오는데 오류 발생!");
        return;
      }
      
      for (Board board : boards) {
        out.printf("%3d, %-20s, %s, %d, %d, %d\n", 
            board.getNo(), board.getContents(), 
            board.getCreatedDate(), board.getViewCount(), 
            board.getWriterNo(), board.getLessonNo());
      }
      
    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }
  
  @CommandMapping("/board/update")
  public void update(BufferedReader in, PrintWriter out) {
    try {
      out.print("번호?\n!{}!\n"); out.flush();
      int no = Integer.parseInt(in.readLine());

      Board board = boardDao.detail(no);
      if (board == null) { 
        out.println("해당 게시글을 찾을 수 없습니다.");
        return;
      }

      out.print("내용?\n!{}!\n"); out.flush();
      String input = in.readLine();
      if (input.length() > 0) 
        board.setContents(input);

      boardDao.update(board);

      out.println("게시글을 변경했습니다.");

    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }
}
