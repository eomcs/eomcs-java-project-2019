package com.eomcs.lms;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import com.eomcs.lms.handler.BoardHandler;
import com.eomcs.lms.handler.LessonHandler;
import com.eomcs.lms.handler.MemberHandler;

public class App {

  static Scanner keyboard = new Scanner(System.in);
  
  // java.util.Stack에서 제공하는 Iterator는 실제 Stack의 수퍼 클래스인 Vector의 iterator()가 리턴한 객체이고,
  // Vector가 제공하는 Iterator는 FIFO 방식으로 값을 꺼내준다.
  // 스택의 LIFO 방식으로 값을 꺼내려면 java.util.ArrayDeque를 사용해야 한다.
  // java.util.ArrayDeque에서 제공하는 Iterator는 LIFO 방식으로 값을 꺼내준다.
  static Deque<String> commandHistory = new ArrayDeque<>();
  
  // java.util.LinkedList 클래스는 java.util.Queue의 구현체이다.
  static Queue<String> commandHistory2 = new LinkedList<>();

  public static void main(String[] args) {

    LessonHandler lessonHandler = new LessonHandler(keyboard, new ArrayList<>());
    MemberHandler memberHandler = new MemberHandler(keyboard, new LinkedList<>());
    BoardHandler boardHandler1 = new BoardHandler(keyboard, new ArrayList<>());
    BoardHandler boardHandler2 = new BoardHandler(keyboard, new LinkedList<>());

    while (true) {
      String command = prompt();

      // 사용자가 입력한 명령을 스택에 보관한다.
      commandHistory.push(command);
      
      // 사용자가 입력한 명령을 큐에 보관한다.
      commandHistory2.offer(command);

      if (command.equals("/lesson/add")) {
        lessonHandler.addLesson();

      } else if (command.equals("/lesson/list")) {
        lessonHandler.listLesson();

      } else if (command.equals("/lesson/detail")) {
        lessonHandler.detailLesson();

      } else if (command.equals("/lesson/update")) {
        lessonHandler.updateLesson();

      } else if (command.equals("/lesson/delete")) {
        lessonHandler.deleteLesson();

      } else if (command.equals("/member/add")) {
        memberHandler.addMember();

      } else if (command.equals("/member/list")) {
        memberHandler.listMember();

      } else if (command.equals("/member/detail")) {
        memberHandler.detailMember();

      } else if (command.equals("/member/update")) {
        memberHandler.updateMember();

      } else if (command.equals("/member/delete")) {
        memberHandler.deleteMember();

      } else if (command.equals("/board/add")) {
        boardHandler1.addBoard();

      } else if (command.equals("/board/list")) {
        boardHandler1.listBoard();

      } else if (command.equals("/board/detail")) {
        boardHandler1.detailBoard();

      } else if (command.equals("/board/update")) {
        boardHandler1.updateBoard();

      } else if (command.equals("/board/delete")) {
        boardHandler1.deleteBoard();

      } else if (command.equals("/board2/add")) {
        boardHandler2.addBoard();

      } else if (command.equals("/board2/list")) {
        boardHandler2.listBoard();

      } else if (command.equals("/board2/detail")) {
        boardHandler2.detailBoard();

      } else if (command.equals("/board2/update")) {
        boardHandler2.updateBoard();

      } else if (command.equals("/board2/delete")) {
        boardHandler2.deleteBoard();

      } else if (command.equals("quit")) {
        System.out.println("안녕!");
        break;

      } else if (command.equals("history")) {
        printCommandHistory(commandHistory.iterator());

      } else if (command.equals("history2")) {
        printCommandHistory(commandHistory2.iterator());
        
      } else {
        System.out.println("실행할 수 없는 명령입니다.");
      }

      System.out.println(); // 결과 출력 후 빈 줄 출력
    }

    keyboard.close();
  }
 
  private static void printCommandHistory(Iterator<String> iterator) {
    try {
      int count = 1;
      while (iterator.hasNext()) {
        System.out.println(iterator.next());
        
        if (count % 5 == 0) {
          System.out.print(":");
          if (keyboard.nextLine().equalsIgnoreCase("q"))
            break;
        }
        count++;
      }
    } catch (Exception e) {
      System.out.println("명령어 목록을 출력하는데 실패했습니다.");
    }
  }
  
  private static String prompt() {
    System.out.print("명령> ");
    return keyboard.nextLine().toLowerCase();
  }
}
