package com.eomcs.lms;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import com.eomcs.lms.domain.Board;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.lms.domain.Member;
import com.eomcs.lms.handler.BoardAddCommand;
import com.eomcs.lms.handler.BoardDeleteCommand;
import com.eomcs.lms.handler.BoardDetailCommand;
import com.eomcs.lms.handler.BoardListCommand;
import com.eomcs.lms.handler.BoardUpdateCommand;
import com.eomcs.lms.handler.Command;
import com.eomcs.lms.handler.LessonAddCommand;
import com.eomcs.lms.handler.LessonDeleteCommand;
import com.eomcs.lms.handler.LessonDetailCommand;
import com.eomcs.lms.handler.LessonListCommand;
import com.eomcs.lms.handler.LessonUpdateCommand;
import com.eomcs.lms.handler.MemberAddCommand;
import com.eomcs.lms.handler.MemberDeleteCommand;
import com.eomcs.lms.handler.MemberDetailCommand;
import com.eomcs.lms.handler.MemberListCommand;
import com.eomcs.lms.handler.MemberUpdateCommand;

public class App {

  static Scanner keyboard = new Scanner(System.in);
  static Stack<String> commandHistory = new Stack<>();
  static Queue<String> commandHistory2 = new LinkedList<>();
  static ArrayList<Lesson> lessonList = new ArrayList<>();
  static LinkedList<Member> memberList = new LinkedList<>();
  static ArrayList<Board> boardList = new ArrayList<>();
  
  public static void main(String[] args) {
    
    loadLessonData();
    loadMemberData();
    loadBoardData();

    Map<String,Command> commandMap = new HashMap<>();
    commandMap.put("/lesson/add", new LessonAddCommand(keyboard, lessonList));
    commandMap.put("/lesson/list", new LessonListCommand(keyboard, lessonList));
    commandMap.put("/lesson/detail", new LessonDetailCommand(keyboard, lessonList));
    commandMap.put("/lesson/update", new LessonUpdateCommand(keyboard, lessonList));
    commandMap.put("/lesson/delete", new LessonDeleteCommand(keyboard, lessonList));

    commandMap.put("/member/add", new MemberAddCommand(keyboard, memberList));
    commandMap.put("/member/list", new MemberListCommand(keyboard, memberList));
    commandMap.put("/member/detail", new MemberDetailCommand(keyboard, memberList));
    commandMap.put("/member/update", new MemberUpdateCommand(keyboard, memberList));
    commandMap.put("/member/delete", new MemberDeleteCommand(keyboard, memberList));
    
    commandMap.put("/board/add", new BoardAddCommand(keyboard, boardList));
    commandMap.put("/board/list", new BoardListCommand(keyboard, boardList));
    commandMap.put("/board/detail", new BoardDetailCommand(keyboard, boardList));
    commandMap.put("/board/update", new BoardUpdateCommand(keyboard, boardList));
    commandMap.put("/board/delete", new BoardDeleteCommand(keyboard, boardList));
    
    while (true) {
      String command = prompt();

      // 사용자가 입력한 명령을 스택에 보관한다.
      commandHistory.push(command);
      
      // 사용자가 입력한 명령을 큐에 보관한다.
      commandHistory2.offer(command);
      
      // 사용자가 입력한 명령으로 Command 객체를 찾는다.
      Command commandHandler = commandMap.get(command);
      
      if (commandHandler != null) {
        try {
          commandHandler.execute();
        } catch (Exception e) {
          System.out.println("명령어 실행 중 오류 발생 : " + e.toString());
        }
      } else if (command.equals("quit")) {
        System.out.println("안녕!");
        break;
        
      } else if (command.equals("history")) {
        printCommandHistory();
        
      } else if (command.equals("history2")) {
        printCommandHistory2();
        
      } else {
        System.out.println("실행할 수 없는 명령입니다.");
      }
      
      System.out.println(); 
    }

    keyboard.close();
    
    saveLessonData();
    saveMemberData();
    saveBoardData();
  }

  @SuppressWarnings("unchecked")
  private static void printCommandHistory() {
    Stack<String> temp = (Stack<String>) commandHistory.clone();
    
    while (temp.size() > 0) {
      System.out.println(temp.pop());
    }
  }
  
  @SuppressWarnings("unchecked")
  private static void printCommandHistory2() {
    Queue<String> temp = (Queue<String>) ((LinkedList<String>) commandHistory2).clone();
    
    while (temp.size() > 0) {
      System.out.println(temp.poll());
    }
  }

  private static String prompt() {
    System.out.print("명령> ");
    return keyboard.nextLine().toLowerCase();
  }
  
  private static void loadLessonData() {
    FileReader in0 = null;
    BufferedReader in = null;
    
    try {
      
      File file = new File("lesson.csv");
      if (!file.exists()) {
        file.createNewFile();
      }
      
      in0 = new FileReader(file);
      in = new BufferedReader(in0);
      
      String line = null; // 형식: 번호,수업명,설명,시작일,종료일,총수업시간,일수업시간
      
      while ((line = in.readLine()) != null) {
        String[] values = line.split(",");
        
        Lesson lesson = new Lesson();
        lesson.setNo(Integer.parseInt(values[0]));
        lesson.setTitle(values[1]);
        lesson.setContents(values[2]);
        lesson.setStartDate(Date.valueOf(values[3]));
        lesson.setEndDate(Date.valueOf(values[4]));
        lesson.setTotalHours(Integer.parseInt(values[5]));
        lesson.setDayHours(Integer.parseInt(values[5]));
        
        lessonList.add(lesson);
      }
      
    } catch (Exception e) {
      System.out.println("수업 데이터를 읽는 중 오류 발생: " + e.toString());
      
    } finally {
      try {in.close();} catch (Exception e) {}
      try {in0.close();} catch (Exception e) {}
    }
  }
  
  private static void saveLessonData() {
    try (FileWriter out0 = new FileWriter("lesson.csv");
        BufferedWriter out1 = new BufferedWriter(out0);
        PrintWriter out = new PrintWriter(out1);) {
      
      // 파일 형식: 번호,수업명,설명,시작일,종료일,총수업시간,일수업시간
      for (Lesson l : lessonList) {
        out.printf("%d,%s,%s,%s,%s,%d,%d\n", 
            l.getNo(), l.getTitle(), l.getContents(),
            l.getStartDate(), l.getEndDate(),
            l.getTotalHours(), l.getDayHours());
      }
      
    } catch (Exception e) {
      System.out.println("수업 데이터를 쓰는 중 오류 발생: " + e.toString());
    }
  }
  
  private static void loadMemberData() {
    FileReader in0 = null;
    BufferedReader in = null;
    
    try {
      
      File file = new File("member.csv");
      if (!file.exists()) {
        file.createNewFile();
      }
      
      in0 = new FileReader(file);
      in = new BufferedReader(in0);
      
      String line = null; // 형식: 번호,이름,이메일,암호,사진,전화,가입일
      
      while ((line = in.readLine()) != null) {
        String[] values = line.split(",");
        
        Member member = new Member();
        member.setNo(Integer.parseInt(values[0]));
        member.setName(values[1]);
        member.setEmail(values[2]);
        member.setPassword(values[3]);
        member.setPhoto(values[4]);
        member.setTel(values[5]);
        member.setRegisteredDate(Date.valueOf(values[6]));
        
        memberList.add(member);
      }
      
    } catch (Exception e) {
      System.out.println("회원 데이터를 읽는 중 오류 발생: " + e.toString());
      
    } finally {
      try {in.close();} catch (Exception e) {}
      try {in0.close();} catch (Exception e) {}
    }
  }
  
  private static void saveMemberData() {
    try (FileWriter out0 = new FileWriter("member.csv");
        BufferedWriter out1 = new BufferedWriter(out0);
        PrintWriter out = new PrintWriter(out1);) {
      
      // 파일 형식: 번호,이름,이메일,암호,사진,전화,가입일
      for (Member m : memberList) {
        out.printf("%d,%s,%s,%s,%s,%s,%s\n", 
            m.getNo(), m.getName(), m.getEmail(), m.getPassword(), m.getPhoto(),
            m.getTel(), m.getRegisteredDate());
      }
      
    } catch (Exception e) {
      System.out.println("회원 데이터를 쓰는 중 오류 발생: " + e.toString());
    }
  }
  
  private static void loadBoardData() {
    FileReader in0 = null;
    BufferedReader in = null;
    
    try {
      
      File file = new File("board.csv");
      if (!file.exists()) {
        file.createNewFile();
      }
      
      in0 = new FileReader(file);
      in = new BufferedReader(in0);
      
      String line = null; // 형식: 번호,내용,등록일,조회수
      
      while ((line = in.readLine()) != null) {
        String[] values = line.split(",");
        
        Board board = new Board();
        board.setNo(Integer.parseInt(values[0]));
        board.setContents(values[1]);
        board.setCreatedDate(Date.valueOf(values[2]));
        board.setViewCount(Integer.parseInt(values[3]));
        
        boardList.add(board);
      }
      
    } catch (Exception e) {
      System.out.println("게시글 데이터를 읽는 중 오류 발생: " + e.toString());
      
    } finally {
      try {in.close();} catch (Exception e) {}
      try {in0.close();} catch (Exception e) {}
    }
  }
  
  private static void saveBoardData() {
    try (FileWriter out0 = new FileWriter("board.csv");
        BufferedWriter out1 = new BufferedWriter(out0);
        PrintWriter out = new PrintWriter(out1);) {
      
      // 파일 형식: 번호,내용,등록일,조회수
      for (Board b : boardList) {
        out.printf("%d,%s,%s,%d\n", 
            b.getNo(), b.getContents(), b.getCreatedDate(), b.getViewCount());
      }
      
    } catch (Exception e) {
      System.out.println("게시글 데이터를 쓰는 중 오류 발생: " + e.toString());
    }
  }
}
