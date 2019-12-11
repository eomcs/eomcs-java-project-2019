package com.eomcs.lms.handler;
import java.util.Scanner;
import com.eomcs.lms.proxy.LessonDaoProxy;

public class LessonDeleteCommand implements Command {

  Scanner keyboard;
  LessonDaoProxy lessonDao;

  public LessonDeleteCommand(Scanner keyboard, LessonDaoProxy lessonDao) {
    this.keyboard = keyboard;
    this.lessonDao = lessonDao;
  }

  @Override
  public void execute() {
    System.out.print("번호? ");
    int no = Integer.parseInt(keyboard.nextLine());

    try {
      if (lessonDao.delete(no) > 0) 
        System.out.println("수업을 삭제했습니다.");
      else 
        System.out.println("해당 수업을 찾을 수 없습니다.");
      
    } catch (Exception e) {
      System.out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }
}
