package com.eomcs.lms.handler;
import java.io.BufferedReader;
import java.io.PrintWriter;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.stereotype.Component;

@Component(value="/lesson/detail")
public class LessonDetailCommand implements Command {

  LessonDao lessonDao;

  public LessonDetailCommand(LessonDao lessonDao) {
    this.lessonDao = lessonDao;
  }

  @Override
  public void execute(BufferedReader in, PrintWriter out) {

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
}
