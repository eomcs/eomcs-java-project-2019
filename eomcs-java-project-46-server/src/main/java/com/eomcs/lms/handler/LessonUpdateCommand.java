package com.eomcs.lms.handler;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.sql.Date;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.domain.Lesson;

public class LessonUpdateCommand implements Command {

  public final String name = "/lesson/update";
  LessonDao lessonDao;

  public LessonUpdateCommand(LessonDao lessonDao) {
    this.lessonDao = lessonDao;
  }

  @Override
  public void execute(BufferedReader in, PrintWriter out) {

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
