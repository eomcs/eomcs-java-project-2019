package com.eomcs.lms.handler;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.sql.Date;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.domain.Lesson;

public class LessonAddCommand implements Command {

  LessonDao lessonDao;

  public LessonAddCommand(LessonDao lessonDao) {
    this.lessonDao = lessonDao;
  }

  @Override
  public void execute(BufferedReader in, PrintWriter out) {
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

}
