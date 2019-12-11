package com.eomcs.lms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.stereotype.Component;

@Component(value="/lesson/search")
public class LessonSearchCommand implements Command {
  
  LessonDao lessonDao;

  public LessonSearchCommand(LessonDao lessonDao) {
    this.lessonDao = lessonDao;
  }
  
  @Override
  public void execute(BufferedReader in, PrintWriter out) {
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
}
