package com.eomcs.lms.handler;
import java.util.Scanner;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.lms.proxy.LessonDaoProxy;

public class LessonDetailCommand implements Command {

  Scanner keyboard;
  LessonDaoProxy lessonDao;

  public LessonDetailCommand(Scanner keyboard, LessonDaoProxy lessonDao) {
    this.keyboard = keyboard;
    this.lessonDao = lessonDao;
  }

  @Override
  public void execute() {
    System.out.print("번호? ");
    int no = Integer.parseInt(keyboard.nextLine());

    try {
      Lesson lesson = lessonDao.detail(no);
      if (lesson == null) { 
        System.out.println("해당 수업을 찾을 수 없습니다.");
        return;
      }
      
      System.out.printf("수업명: %s\n", lesson.getTitle());
      System.out.printf("설명: %s\n", lesson.getContents());
      System.out.printf("기간: %s ~ %s\n", lesson.getStartDate(), lesson.getEndDate());
      System.out.printf("총수업시간: %d\n", lesson.getTotalHours());
      System.out.printf("일수업시간: %d\n", lesson.getDayHours());
      
    } catch (Exception e) {
      System.out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }
}
