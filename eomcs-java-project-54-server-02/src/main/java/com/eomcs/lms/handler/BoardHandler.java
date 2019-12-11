package com.eomcs.lms.handler;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
  public void add(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    Board board = new Board();
    board.setContents(request.getParameter("contents"));
    board.setWriterNo(Integer.parseInt(request.getParameter("writerNo")));
    board.setLessonNo(Integer.parseInt(request.getParameter("lessonNo")));

    boardDao.add(board);
    
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head><title>게시물관리</title></head>");
    out.println("<body>");
    out.println("<h1>게시물 추가</h1>");
    out.println("<p>게시물을 저장했습니다.</p>");
    out.println("</body>");
    out.println("</html>");
    
    response.setHeader("Refresh", "1;url=list");
  }
  
  @CommandMapping("/board/delete")
  public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    int no = Integer.parseInt(request.getParameter("no"));
    
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head><title>게시물관리</title></head>");
    out.println("<body>");
    out.println("<h1>게시물 삭제</h1>");

    if (boardDao.delete(no) > 0) 
      out.println("<p>게시물을 삭제했습니다.</p>");
    else 
      out.println("<p>해당 게시물을 찾을 수 없습니다.</p>");
    
    out.println("</body>");
    out.println("</html>");
    
    response.setHeader("Refresh", "1;url=list");
  }
  
  @CommandMapping("/board/detail")
  public void detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    int no = Integer.parseInt(request.getParameter("no"));

    Board board = boardDao.detail(no);

    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head><title>게시물관리</title></head>");
    out.println("<body>");
    out.println("<h1>게시물 상세 정보</h1>");

    if (board == null) { 
      out.println("<p>해당 게시물을 찾을 수 없습니다.</p>");

    } else {
      out.println("<form action='/app/board/update' method='post'>");
      out.printf("<input type='hidden' name='no' value='%d'>\n", no);
      out.println("<table border='1'>");
      out.printf("<tr><th>내용</th>"
          + "<td><textarea name='contents' cols='50' rows='5'>%s</textarea></td></tr>\n", 
          board.getContents());
      out.printf("<tr><th>작성일</th><td>%s</td></tr>\n", board.getCreatedDate());
      out.printf("<tr><th>조회수</th><td>%d</td></tr>\n", board.getViewCount());
      out.printf("<tr><th>작성자</th><td>%d</td></tr>\n", board.getWriterNo());
      out.printf("<tr><th>수업</th><td>%d</td></tr>\n", board.getLessonNo());
      out.println("<tr><th></th><td><button>변경</button>");
      out.printf("<a href='delete?no=%d'>삭제</a></td></tr>\n", no);
      out.println("</table>");
      out.println("</form>");
    }
    out.println("</body>");
    out.println("</html>");
  }
  
  @CommandMapping("/board/list")
  public void list(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    List<Board> boards = boardDao.list();
    
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head><title>게시물관리</title></head>");
    out.println("<body>");
    out.println("<h1>게시물 목록</h1>");

    out.println("<a href='/board/form.html'>새 게시물</a>");
    out.println("<table border='1'>");
    out.println("<tr> <th>번호</th> <th>내용</th> <th>작성일</th> <th>조회수</th> <th>작성자</th> </tr>");

    for (Board board : boards) {
      out.println("<tr>");
      out.printf("<td>%d</td>\n", board.getNo());
      out.printf("<td><a href='detail?no=%d'>%s</a></td>\n", board.getNo(), board.getContents());
      out.printf("<td>%s</td>\n", board.getCreatedDate());
      out.printf("<td>%d</td>\n", board.getViewCount());
      out.printf("<td>%d</td>\n", board.getWriterNo());
      out.println("</tr>");
    }
    out.println("</table>");

    out.println("</body>");
    out.println("</html>");
  }
  
  @CommandMapping("/board/update")
  public void update(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    Board board = new Board();
    board.setNo(Integer.parseInt(request.getParameter("no")));
    board.setContents(request.getParameter("contents"));
    
    boardDao.update(board);
    
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head><title>게시물관리</title></head>");
    out.println("<body>");
    out.println("<h1>게시물 변경</h1>");
    out.println("<p>게시물을 변경했습니다.</p>");
    out.println("</body>");
    out.println("</html>");
    
    response.setHeader("Refresh", "1;url=list");
  }
}
