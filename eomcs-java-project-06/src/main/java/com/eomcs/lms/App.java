package com.eomcs.lms;

import java.sql.Date;
import java.util.Scanner;

public class App {

  public static void main(String[] args) {
    
    Scanner keyboard = new Scanner(System.in);

    final int LENGTH = 10;
    
    int[] no = new int[LENGTH];
    String[] title = new String[LENGTH];
    String[] contents = new String[LENGTH];
    Date[] startDate = new Date[LENGTH];
    Date[] endDate = new Date[LENGTH];
    int[] totalHours = new int[LENGTH];
    int[] dayHours = new int[LENGTH];
    
    int i = 0;
    while (i < LENGTH) {
      System.out.print("번호? ");
      no[i] = Integer.parseInt(keyboard.nextLine());
      
      System.out.print("수업명? ");
      title[i] = keyboard.nextLine();
      
      System.out.print("설명? ");
      contents[i] = keyboard.nextLine();
      
      System.out.print("시작일? ");
      startDate[i] = Date.valueOf(keyboard.nextLine());
      
      System.out.print("종료일? ");
      endDate[i] = Date.valueOf(keyboard.nextLine());
      
      System.out.print("총수업시간? ");
      totalHours[i] = Integer.parseInt(keyboard.nextLine());
      
      System.out.print("일수업시간? ");
      dayHours[i] = Integer.parseInt(keyboard.nextLine());
      
      i++; // 배열의 인덱스를 증가시킨다.
      
      // 사용자가 입력한 값을 소문자로 변환한다.
      System.out.print("\n계속 입력하시겠습니까?(Y/n) ");
      String answer = keyboard.nextLine().toLowerCase();
      
      // 입력 값이 "Y", "y", "" 이 아니면, 입력을 종료한다. 
      if (!answer.equals("y") && answer.length() > 0) {
        break;
      }

      System.out.println();
    }
    
    keyboard.close();
    
    System.out.println(); // 빈 줄 출력
    
    // 배열에 입력한 개수만큼 출력한다.
    for (int j = 0; j < i; j++) {
      System.out.printf("%3d, %-15s, %10s ~ %10s, %4d\n", 
          no[j], title[j], startDate[j], endDate[j], totalHours[j]);
    }
  }
}
