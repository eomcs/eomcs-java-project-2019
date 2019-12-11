package com.eomcs.lms.handler;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.stereotype.CommandMapping;
import com.eomcs.stereotype.Component;

@Component
public class LessonHandler {

  LessonDao lessonDao;

  public LessonHandler(LessonDao lessonDao) {
    this.lessonDao = lessonDao;
  }

  @CommandMapping("/lesson/add")
  public void add(BufferedReader in, PrintWriter out) {
    try {
      Lesson lesson = new Lesson();

      out.print("수업명?\n!{}!\n"); 
      out.flush();
      lesson.setTitle(in.readLine());

      out.print("설명?\n!{}!\n");
      out.flush();
      lesson.setContents(in.readLine());

      out.print("시작일?\n!{}!\n");
      out.flush();
      lesson.setStartDate(Date.valueOf(in.readLine()));

      out.print("종료일?\n!{}!\n");
      out.flush();
      lesson.setEndDate(Date.valueOf(in.readLine()));

      out.print("총수업시간?\n!{}!\n");
      out.flush();
      lesson.setTotalHours(Integer.parseInt(in.readLine()));

      out.print("일수업시간?\n!{}!\n");
      out.flush();
      lesson.setDayHours(Integer.parseInt(in.readLine()));

      out.print("강사?\n!{}!\n");
      out.flush();
      lesson.setOwnerNo(Integer.parseInt(in.readLine()));

      lessonDao.add(lesson);
      out.println("수업을 저장했습니다.");
      
    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }

  @CommandMapping("/lesson/delete")
  public void delete(BufferedReader in, PrintWriter out) {
    try {
      out.print("번호?\n!{}!\n"); out.flush();
      int no = Integer.parseInt(in.readLine());

      if (lessonDao.delete(no) > 0) 
        out.println("수업을 삭제했습니다.");
      else 
        out.println("해당 수업을 찾을 수 없습니다.");

    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }
  
  @CommandMapping("/lesson/detail")
  public void detail(BufferedReader in, PrintWriter out) {

    try {
      out.print("번호?\n!{}!\n");
      out.flush();
      int no = Integer.parseInt(in.readLine());
      
      Lesson lesson = lessonDao.detail(no);
      if (lesson == null) { 
        out.println("해당 수업을 찾을 수 없습니다.");
        return;
      }
      
      out.printf("수업명: %s\n", lesson.getTitle());
      out.printf("설명: %s\n", lesson.getContents());
      out.printf("기간: %s ~ %s\n", lesson.getStartDate(), lesson.getEndDate());
      out.printf("총수업시간: %d\n", lesson.getTotalHours());
      out.printf("일수업시간: %d\n", lesson.getDayHours());
      out.printf("강사: %d\n", lesson.getOwnerNo());
      
    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }
  
  @CommandMapping("/lesson/list")
  public void list(BufferedReader in, PrintWriter out) {
    try {
      List<Lesson> lessons = lessonDao.list(null);
      if (lessons == null) { 
        out.println("서버에서 데이터를 가져오는데 오류 발생!");
        return;
      }
      
      for (Lesson lesson : lessons) {
        out.printf("%3d, %-15s, %10s ~ %10s, %4d\n", 
            lesson.getNo(), lesson.getTitle(), 
            lesson.getStartDate(), lesson.getEndDate(), lesson.getTotalHours());
      }
      
    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }
  
  @CommandMapping("/lesson/search")
  public void search(BufferedReader in, PrintWriter out) {
    try {
      Map<String,Object> params = new HashMap<>();
      
      out.print("수업명?\n!{}!\n"); out.flush();
      String value = in.readLine();
      if (value.length() > 0) {
        params.put("title", value);
      }
      
      out.print("설명?\n!{}!\n"); out.flush();
      value = in.readLine();
      if (value.length() > 0) {
        params.put("contents", value);
      }
      
      out.print("시작일(<=)?\n!{}!\n"); out.flush();
      value = in.readLine();
      if (value.length() > 0) {
        params.put("startDate", Date.valueOf(value));
      }
      
      out.print("종료일(<=)?\n!{}!\n"); out.flush();
      value = in.readLine();
      if (value.length() > 0) {
        params.put("endDate", Date.valueOf(value));
      }
      
      List<Lesson> lessons = lessonDao.list(params);
      if (lessons == null) { 
        out.println("서버에서 데이터를 가져오는데 오류 발생!");
        return;
      }
      
      for (Lesson lesson : lessons) {
        out.printf("%3d, %-15s, %10s ~ %10s, %4d\n", 
            lesson.getNo(), lesson.getTitle(), 
            lesson.getStartDate(), lesson.getEndDate(), lesson.getTotalHours());
      }
      
    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }
  
  @CommandMapping("/lesson/update")
  public void update(BufferedReader in, PrintWriter out) {

    try {
      out.print("번호?\n!{}!\n"); out.flush();
      int no = Integer.parseInt(in.readLine());

      Lesson lesson = lessonDao.detail(no);
      if (lesson == null) { 
        out.println("해당 게시글을 찾을 수 없습니다.");
        return;
      }
      
      out.printf("수업명(%s)?\n!{}!\n", lesson.getTitle()); out.flush();
      String input = in.readLine();
      if (input.length() > 0) 
        lesson.setTitle(input);
      
      out.printf("설명(%s)?\n!{}!\n", lesson.getContents()); out.flush();
      if ((input = in.readLine()).length() > 0)
        lesson.setContents(input);
      
      out.printf("시작일(%s)?\n!{}!\n", lesson.getStartDate()); out.flush();
      if ((input = in.readLine()).length() > 0)
        lesson.setStartDate(Date.valueOf(input));
      
      out.printf("종료일(%s)?\n!{}!\n", lesson.getEndDate()); out.flush();
      if ((input = in.readLine()).length() > 0)
        lesson.setEndDate(Date.valueOf(input));
      
      out.printf("총수업시간(%d)?\n!{}!\n", lesson.getTotalHours()); out.flush();
      if ((input = in.readLine()).length() > 0)
        lesson.setTotalHours(Integer.parseInt(input));
      
      out.printf("일수업시간(%d)?\n!{}!\n", lesson.getDayHours()); out.flush();
      if ((input = in.readLine()).length() > 0)
        lesson.setDayHours(Integer.parseInt(input));
      
      out.printf("강사(%d)?\n!{}!\n", lesson.getOwnerNo()); out.flush();
      if ((input = in.readLine()).length() > 0)
        lesson.setOwnerNo(Integer.parseInt(input));
      
      lessonDao.update(lesson);
      out.println("수업을 변경했습니다.");
      
    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }
}
