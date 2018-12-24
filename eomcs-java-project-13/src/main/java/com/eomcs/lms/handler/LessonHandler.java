package com.eomcs.lms.handler;

import java.sql.Date;
import java.util.Scanner;
import com.eomcs.lms.domain.Lesson;

public class LessonHandler {

  static final int LENGTH = 10;
  public Scanner keyboard;
  Lesson[] lessons = new Lesson[LENGTH];
  int lessonIdx = 0;
  
  public void listLesson() {
    for (int j = 0; j < this.lessonIdx; j++) {
      System.out.printf("%3d, %-15s, %10s ~ %10s, %4d\n", 
          this.lessons[j].no, this.lessons[j].title, this.lessons[j].startDate, 
          this.lessons[j].endDate, this.lessons[j].totalHours);
    }
  }

  public void addLesson() {
    Lesson lesson = new Lesson();

    System.out.print("번호? ");
    lesson.no = Integer.parseInt(this.keyboard.nextLine());

    System.out.print("수업명? ");
    lesson.title = this.keyboard.nextLine();

    System.out.print("설명? ");
    lesson.contents = this.keyboard.nextLine();

    System.out.print("시작일? ");
    lesson.startDate = Date.valueOf(this.keyboard.nextLine());

    System.out.print("종료일? ");
    lesson.endDate = Date.valueOf(this.keyboard.nextLine());

    System.out.print("총수업시간? ");
    lesson.totalHours = Integer.parseInt(this.keyboard.nextLine());

    System.out.print("일수업시간? ");
    lesson.dayHours = Integer.parseInt(this.keyboard.nextLine());

    // i 번째 배열에 수업 정보를 담고 있는 Lesson 객체(의 주소)를 보관한다.
    this.lessons[this.lessonIdx] = lesson;
    this.lessonIdx++;

    System.out.println("저장하였습니다.");
  }
}
