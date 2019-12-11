package com.eomcs.lms.handler;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.stereotype.CommandMapping;

@Component
public class LessonHandler {

  LessonDao lessonDao;

  public LessonHandler(LessonDao lessonDao) {
    this.lessonDao = lessonDao;
  }

  @CommandMapping("/lesson/add")
  public void add(HttpServletRequest request, HttpServletResponse response) throws Exception {

    Lesson lesson = new Lesson();
    lesson.setTitle(request.getParameter("title"));
    lesson.setContents(request.getParameter("contents"));
    lesson.setStartDate(Date.valueOf(request.getParameter("startDate")));
    lesson.setEndDate(Date.valueOf(request.getParameter("endDate")));
    lesson.setTotalHours(Integer.parseInt(request.getParameter("totalHours")));
    lesson.setDayHours(Integer.parseInt(request.getParameter("dayHours")));
    lesson.setOwnerNo(Integer.parseInt(request.getParameter("ownerNo")));

    lessonDao.add(lesson);

    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head><title>수업관리</title></head>");
    out.println("<body>");
    out.println("<h1>수업 추가</h1>");
    out.println("<p>수업을 저장했습니다.</p>");
    out.println("</body>");
    out.println("</html>");
    
    response.setHeader("Refresh", "1;url=list");
  }

  @CommandMapping("/lesson/delete")
  public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {

    int no = Integer.parseInt(request.getParameter("no"));
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head><title>수업관리</title></head>");
    out.println("<body>");
    out.println("<h1>수업 삭제</h1>");

    if (lessonDao.delete(no) > 0) 
      out.println("<p>수업을 삭제했습니다.</p>");
    else 
      out.println("<p>해당 수업을 찾을 수 없습니다.</p>");
    
    out.println("</body>");
    out.println("</html>");

    response.setHeader("Refresh", "1;url=list");
  }

  @CommandMapping("/lesson/detail")
  public void detail(HttpServletRequest request, HttpServletResponse response) throws Exception {

    int no = Integer.parseInt(request.getParameter("no"));

    Lesson lesson = lessonDao.detail(no);

    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head><title>수업관리</title></head>");
    out.println("<body>");
    out.println("<h1>수업 상세 정보</h1>");

    if (lesson == null) { 
      out.println("<p>해당 수업을 찾을 수 없습니다.</p>");

    } else {
      out.println("<form action='/app/lesson/update' method='post'>");
      out.printf("<input type='hidden' name='no' value='%d'>\n", no);
      out.println("<table border='1'>");
      out.printf("<tr><th>수업명</th>"
          + "<td><input type='text' name='title' value='%s'></td></tr>\n", 
          lesson.getTitle());
      out.printf("<tr><th>설명</th>"
          + "<td><textarea name='contents' cols='50' rows='5'>%s</textarea></td></tr>\n", 
          lesson.getContents());
      out.printf("<tr><th>기간</th>"
          + "<td><input type='text' name='startDate' value='%s'> ~ "
          + "<input type='text' name='endDate' value='%s'></td></tr>\n", 
          lesson.getStartDate(), lesson.getEndDate());
      out.printf("<tr><th>총수업시간</th>"
          + "<td><input type='number' name='totalHours' value='%d'></td></tr>\n", 
          lesson.getTotalHours());
      out.printf("<tr><th>일수업시간</th>"
          + "<td><input type='number' name='dayHours' value='%d'></td></tr>\n", 
          lesson.getDayHours());
      out.printf("<tr><th>강사</th>"
          + "<td><input type='number' name='ownerNo' value='%d'></td></tr>\n", 
          lesson.getOwnerNo());
      out.println("<tr><th></th><td><button>변경</button>");
      out.printf("<a href='delete?no=%d'>삭제</a></td></tr>\n", no);
      out.println("</table>");
      out.println("</form>");
    }
    out.println("</body>");
    out.println("</html>");
  }

  @CommandMapping("/lesson/list")
  public void list(HttpServletRequest request, HttpServletResponse response) throws Exception {

    List<Lesson> lessons = lessonDao.list(null);

    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head><title>수업관리</title></head>");
    out.println("<body>");
    out.println("<h1>수업 목록</h1>");

    out.println("<a href='/lesson/form.html'>새 수업</a>");
    out.println("<table border='1'>");
    out.println("<tr> <th>번호</th> <th>수업</th> <th>수업 기간</th> <th>수업시간</th> </tr>");

    for (Lesson lesson : lessons) {
      out.println("<tr>");
      out.printf("<td>%d</td>\n", lesson.getNo());
      out.printf("<td><a href='detail?no=%d'>%s</a></td>\n", lesson.getNo(), lesson.getTitle());
      out.printf("<td>%s ~ %s</td>\n", lesson.getStartDate(), lesson.getEndDate());
      out.printf("<td>%d</td>\n", lesson.getTotalHours());
      out.println("</tr>");
    }
    out.println("</table>");

    out.println("<p><a href='/lesson/search.html'>검색</a></p>");
    out.println("</body>");
    out.println("</html>");
  }

  @CommandMapping("/lesson/search")
  public void search(HttpServletRequest request, HttpServletResponse response) throws Exception {

    Map<String,Object> params = new HashMap<>();
    String value = request.getParameter("title");
    if (value.length() > 0) {
      params.put("title", value);
    }
    value = request.getParameter("contents");
    if (value.length() > 0) {
      params.put("contents", value);
    }
    value = request.getParameter("startDate");
    if (value.length() > 0) {
      params.put("startDate", Date.valueOf(value));
    }
    value = request.getParameter("endDate");
    if (value.length() > 0) {
      params.put("endDate", Date.valueOf(value));
    }

    List<Lesson> lessons = lessonDao.list(params);

    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head><title>수업관리</title></head>");
    out.println("<body>");
    out.println("<h1>검색 결과</h1>");

    out.println("<table border='1'>");
    out.println("<tr> <th>번호</th> <th>수업</th> <th>수업 기간</th> <th>수업시간</th> </tr>");

    for (Lesson lesson : lessons) {
      out.println("<tr>");
      out.printf("<td>%d</td>\n", lesson.getNo());
      out.printf("<td><a href='detail?no=%d'>%s</a></td>\n", lesson.getNo(), lesson.getTitle());
      out.printf("<td>%s ~ %s</td>\n", lesson.getStartDate(), lesson.getEndDate());
      out.printf("<td>%d</td>\n", lesson.getTotalHours());
      out.println("</tr>");
    }
    out.println("</table>");

    out.println("</body>");
    out.println("</html>");

  }

  @CommandMapping("/lesson/update")
  public void update(HttpServletRequest request, HttpServletResponse response) throws Exception {

    Lesson lesson = new Lesson();
    lesson.setNo(Integer.parseInt(request.getParameter("no")));
    lesson.setTitle(request.getParameter("title"));
    lesson.setContents(request.getParameter("contents"));
    lesson.setStartDate(Date.valueOf(request.getParameter("startDate")));
    lesson.setEndDate(Date.valueOf(request.getParameter("endDate")));
    lesson.setTotalHours(Integer.parseInt(request.getParameter("totalHours")));
    lesson.setDayHours(Integer.parseInt(request.getParameter("dayHours")));
    lesson.setOwnerNo(Integer.parseInt(request.getParameter("ownerNo")));

    lessonDao.update(lesson);

    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head><title>수업관리</title></head>");
    out.println("<body>");
    out.println("<h1>수업 변경</h1>");
    out.println("<p>수업을 변경했습니다.</p>");
    out.println("</body>");
    out.println("</html>");

    response.setHeader("Refresh", "1;url=list");
  }
}
