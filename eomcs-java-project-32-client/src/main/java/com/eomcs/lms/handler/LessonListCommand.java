package com.eomcs.lms.handler;

import java.util.Scanner;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.lms.proxy.LessonDaoProxy;

public class LessonListCommand implements Command {
  
  Scanner keyboard;
  LessonDaoProxy lessonDao;

  public LessonListCommand(Scanner keyboard, LessonDaoProxy lessonDao) {
    this.keyboard = keyboard;
    this.lessonDao = lessonDao;
  }
  
  @Override
  public void execute() {
    try {
      Lesson[] lessons = lessonDao.list();
      if (lessons == null) { 
        System.out.println("서버에서 데이터를 가져오는데 오류 발생!");
        return;
      }
      
      for (Lesson lesson : lessons) {
        System.out.printf("%3d, %-15s, %10s ~ %10s, %4d\n", 
            lesson.getNo(), lesson.getTitle(), 
            lesson.getStartDate(), lesson.getEndDate(), lesson.getTotalHours());
      }
      
    } catch (Exception e) {
      System.out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }
}
