package com.eomcs.lms.handler;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.dao.PhotoFileDao;
import com.eomcs.lms.domain.PhotoBoard;
import com.eomcs.lms.domain.PhotoFile;
import com.eomcs.stereotype.CommandMapping;

@Component
public class PhotoBoardHandler {

  PhotoBoardDao photoBoardDao;
  PhotoFileDao photoFileDao;

  public PhotoBoardHandler(PhotoBoardDao photoBoardDao, PhotoFileDao photoFileDao) {
    this.photoBoardDao = photoBoardDao;
    this.photoFileDao = photoFileDao;
  }

  @Transactional(rollbackFor=Exception.class)
  @CommandMapping("/photoboard/add")
  public void add(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    PhotoBoard board = new PhotoBoard();
    board.setTitle(request.getParameter("title"));
    board.setLessonNo(Integer.parseInt(request.getParameter("lessonNo")));
    
    photoBoardDao.add(board);
    
    ArrayList<PhotoFile> photos = new ArrayList<>();
    
    String[] files = request.getParameterValues("files");
    
    if (files == null || (files.length == 1 && files[0].length() == 0))
      throw new Exception("최소 한 개의 사진 파일을 등록해야 합니다.");
    
    for (String file : files) {
      PhotoFile photoFile = new PhotoFile();
      photoFile.setBoardNo(board.getNo());
      photoFile.setFilepath(file);
      
      photos.add(photoFile);
    }
    
    photoFileDao.add(photos);
    
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head><title>사진관리</title></head>");
    out.println("<body>");
    out.println("<h1>사진 추가</h1>");
    out.println("<p>사진을 저장했습니다.</p>");
    out.println("</body>");
    out.println("</html>");
    
    response.setHeader("Refresh", "1;url=list");
  }

  @Transactional(rollbackFor=Exception.class)
  @CommandMapping("/photoboard/delete")
  public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    int no = Integer.parseInt(request.getParameter("no"));
    
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head><title>사진관리</title></head>");
    out.println("<body>");
    out.println("<h1>사진 삭제</h1>");

    // 게시물에 첨부된 사진 파일을 먼저 삭제한다.
    photoFileDao.deleteByBoard(no);

    if (photoBoardDao.delete(no) > 0) {
      out.println("<p>사진을 삭제했습니다.</p>");
    } else { 
      out.println("<p>해당 사진을 찾을 수 없습니다.</p>");
    }
    
    out.println("</body>");
    out.println("</html>");
    
    response.setHeader("Refresh", "1;url=list");
  }

  @CommandMapping("/photoboard/detail2")
  public void detail2(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int no = Integer.parseInt(request.getParameter("no"));

    PhotoBoard board = photoBoardDao.detail2(no);
    if (board == null) { 
      throw new Exception("해당 사진이 없습니다.");
    }

    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head><title>사진관리</title></head>");
    out.println("<body>");
    out.println("<h1>사진 상세 정보</h1>");

    out.println("<form action='/app/photoboard/update' method='post'>");
    out.printf("<input type='hidden' name='no' value='%d'>\n", no);
    out.println("<table border='1'>");
    out.printf("<tr><th>제목</th><td><input type='text' name='title' value='%s'></td></tr>\n", 
        board.getTitle());
    out.printf("<tr><th>작성일</th><td>%s</td></tr>\n", board.getCreatedDate());
    out.printf("<tr><th>조회수</th><td>%d</td></tr>\n", board.getViewCount());
    out.printf("<tr><th>수업</th><td>%d</td></tr>\n", board.getLessonNo());
    out.println("<tr><th>사진 파일</th><td>");
    
    List<PhotoFile> files = board.getPhotos();
    for (PhotoFile file : files) {
      out.printf("%s<br>\n", file.getFilepath());
    }
    out.println("<input type='file' name='files' multiple></td></tr>");
    out.println("<tr><th></th><td><button>변경</button>");
    out.printf("<a href='delete?no=%d'>삭제</a></td></tr>\n", no);
    out.println("</table>");
    out.println("</form>");
    
    out.println("</body>");
    out.println("</html>");
  }

  @CommandMapping("/photoboard/detail")
  public void detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int no = Integer.parseInt(request.getParameter("no"));

    PhotoBoard board = photoBoardDao.detail(no);
    if (board == null) { 
      throw new Exception("해당 사진이 없습니다.");
    }

    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head><title>사진관리</title></head>");
    out.println("<body>");
    out.println("<h1>사진 상세 정보</h1>");

    out.println("<form action='/app/photoboard/update' method='post'>");
    out.printf("<input type='hidden' name='no' value='%d'>\n", no);
    out.println("<table border='1'>");
    out.printf("<tr><th>제목</th><td><input type='text' name='title' value='%s'></td></tr>\n", 
        board.getTitle());
    out.printf("<tr><th>작성일</th><td>%s</td></tr>\n", board.getCreatedDate());
    out.printf("<tr><th>조회수</th><td>%d</td></tr>\n", board.getViewCount());
    out.printf("<tr><th>수업</th><td>%d</td></tr>\n", board.getLessonNo());
    out.println("<tr><th>사진 파일</th><td>");
    
    List<PhotoFile> files = photoFileDao.listByBoard(no);
    for (PhotoFile file : files) {
      out.printf("%s<br>\n", file.getFilepath());
    }
    out.println("<input type='file' name='files' multiple></td></tr>");
    out.println("<tr><th></th><td><button>변경</button>");
    out.printf("<a href='delete?no=%d'>삭제</a></td></tr>\n", no);
    out.println("</table>");
    out.println("</form>");
    
    out.println("</body>");
    out.println("</html>");
  }

  @CommandMapping("/photoboard/list")
  public void list(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    List<PhotoBoard> boards = photoBoardDao.list();
    
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head><title>사진관리</title></head>");
    out.println("<body>");
    out.println("<h1>사진 목록</h1>");

    out.println("<a href='/photoboard/form.html'>새 사진</a>");
    out.println("<table border='1'>");
    out.println("<tr> <th>번호</th> <th>제목</th> <th>작성일</th> <th>조회수</th> <th>수업</th> </tr>");

    for (PhotoBoard board : boards) {
      out.println("<tr>");
      out.printf("<td>%d</td>\n", board.getNo());
      out.printf("<td><a href='detail?no=%d'>%s</a></td>\n", board.getNo(), board.getTitle());
      out.printf("<td>%s</td>\n", board.getCreatedDate());
      out.printf("<td>%d</td>\n", board.getViewCount());
      out.printf("<td>%d</td>\n", board.getLessonNo());
      out.println("</tr>");
    }
    out.println("</table>");

    out.println("</body>");
    out.println("</html>");
  }

  @Transactional(rollbackFor=Exception.class)
  @CommandMapping("/photoboard/update")
  public void update(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    PhotoBoard board = new PhotoBoard();
    board.setNo(Integer.parseInt(request.getParameter("no")));
    board.setTitle(request.getParameter("title"));
    
    photoBoardDao.update(board);
    
    String[] files = request.getParameterValues("files");
    
    if (files != null && files[0].length() > 0) {
      photoFileDao.deleteByBoard(board.getNo());
      
      ArrayList<PhotoFile> photos = new ArrayList<>();
      for (String file : files) {
        PhotoFile photoFile = new PhotoFile();
        photoFile.setBoardNo(board.getNo());
        photoFile.setFilepath(file);
        
        photos.add(photoFile);
      }
      photoFileDao.add(photos);
    }
    
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head><title>사진관리</title></head>");
    out.println("<body>");
    out.println("<h1>사진 변경</h1>");
    out.println("<p>사진을 변경했습니다.</p>");
    out.println("</body>");
    out.println("</html>");
    
    response.setHeader("Refresh", "1;url=list");
  }
}
