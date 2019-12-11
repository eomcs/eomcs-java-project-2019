package com.eomcs.lms;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
    DataInputStream in = null;
    
    try {
      
      File file = new File("lesson.bin");
      if (!file.exists()) {
        file.createNewFile();
        return;
      }
      
      in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
      
      // 파일의 첫 번째 데이터가 저장된 데이터의 개수(int)이다.
      int length = in.readInt();
      
      for (int i = 0; i < length; i++) {
        // 데이터 읽기 순서: 
        // 번호(int),수업명(String),설명(String),시작일(String),종료일(String),총수업시간(int),일수업시간(int)
        Lesson lesson = new Lesson();
        lesson.setNo(in.readInt());
        lesson.setTitle(in.readUTF());
        lesson.setContents(in.readUTF());
        lesson.setStartDate(Date.valueOf(in.readUTF()));
        lesson.setEndDate(Date.valueOf(in.readUTF()));
        lesson.setTotalHours(in.readInt());
        lesson.setDayHours(in.readInt());
        
        lessonList.add(lesson);
      }
      
    } catch (Exception e) {
      System.out.println("수업 데이터를 읽는 중 오류 발생: " + e.toString());
      
    } finally {
      try {in.close();} catch (Exception e) {}
    }
  }
  
  private static void saveLessonData() {
    try (DataOutputStream out = new DataOutputStream(new BufferedOutputStream(
        new FileOutputStream("lesson.bin")))) {
      
      // 첫 번째로 데이터의 개수(int)를 먼저 출력한다.
      out.writeInt(lessonList.size());
      
      for (Lesson l : lessonList) {
        // 데이터 쓰기 순서: 
        // 번호(int),수업명(String),설명(String),시작일(String),종료일(String),총수업시간(int),일수업시간(int)
        out.writeInt(l.getNo());
        out.writeUTF(l.getTitle());
        out.writeUTF(l.getContents());
        out.writeUTF(l.getStartDate().toString());
        out.writeUTF(l.getEndDate().toString());
        out.writeInt(l.getTotalHours());
        out.writeInt(l.getDayHours());
      }
      
    } catch (Exception e) {
      System.out.println("수업 데이터를 쓰는 중 오류 발생: " + e.toString());
    }
  }
  
  private static void loadMemberData() {
    DataInputStream in = null;
    
    try {
      
      File file = new File("member.bin");
      if (!file.exists()) {
        file.createNewFile();
        return;
      }
      
      in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
      
      // 파일의 첫 번째 데이터가 저장된 데이터의 개수(int)이다.
      int length = in.readInt();
      
      for (int i = 0; i < length; i++) {
        // 데이터 읽기 순서: 
        // 번호(int),이름(String),이메일(String),암호(String),사진(String),전화(String),가입일(String)
        
        Member member = new Member();
        member.setNo(in.readInt());
        member.setName(in.readUTF());
        member.setEmail(in.readUTF());
        member.setPassword(in.readUTF());
        member.setPhoto(in.readUTF());
        member.setTel(in.readUTF());
        member.setRegisteredDate(Date.valueOf(in.readUTF()));
        
        memberList.add(member);
      }
      
    } catch (Exception e) {
      System.out.println("회원 데이터를 읽는 중 오류 발생: " + e.toString());
      
    } finally {
      try {in.close();} catch (Exception e) {}
    }
  }
  
  private static void saveMemberData() {
    try (DataOutputStream out = new DataOutputStream(new BufferedOutputStream(
        new FileOutputStream("member.bin")))) {
      
      // 첫 번째로 데이터의 개수(int)를 먼저 출력 한다.
      out.writeInt(memberList.size());

      for (Member m : memberList) {
        // 데이터 읽기 순서: 
        // 번호(int),이름(String),이메일(String),암호(String),사진(String),전화(String),가입일(String)
        out.writeInt(m.getNo());
        out.writeUTF(m.getName());
        out.writeUTF(m.getEmail());
        out.writeUTF(m.getPassword());
        out.writeUTF(m.getPhoto());
        out.writeUTF(m.getTel());
        out.writeUTF(m.getRegisteredDate().toString());
      }
      
    } catch (Exception e) {
      System.out.println("회원 데이터를 쓰는 중 오류 발생: " + e.toString());
    }
  }
  
  private static void loadBoardData() {
    DataInputStream in = null;
    
    try {
      
      File file = new File("board.bin");
      if (!file.exists()) {
        file.createNewFile();
        return;
      }
      
      in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
      
      // 파일의 첫 번째 데이터가 저장된 데이터의 개수(int)이다.
      int length = in.readInt();
      
      for (int i = 0; i < length; i++) {
        // 데이터 읽기 순서:
        // 번호(int),내용(String),등록일(String),조회수(int)

        Board board = new Board();
        board.setNo(in.readInt());
        board.setContents(in.readUTF());
        board.setCreatedDate(Date.valueOf(in.readUTF()));
        board.setViewCount(in.readInt());
        
        boardList.add(board);
      }
      
    } catch (Exception e) {
      System.out.println("게시글 데이터를 읽는 중 오류 발생: " + e.toString());
      
    } finally {
      try {in.close();} catch (Exception e) {}
    }
  }
  
  private static void saveBoardData() {
    try (DataOutputStream out = new DataOutputStream(new BufferedOutputStream(
        new FileOutputStream("board.bin")))) {
      
      // 첫 번째로 데이터의 개수(int)를 먼저 출력 한다.
      out.writeInt(boardList.size());
      
      for (Board b : boardList) {
        // 데이터 읽기 순서:
        // 번호(int),내용(String),등록일(String),조회수(int)
       
        out.writeInt(b.getNo());
        out.writeUTF(b.getContents());
        out.writeUTF(b.getCreatedDate().toString());
        out.writeInt(b.getViewCount());
      }
      
    } catch (Exception e) {
      System.out.println("게시글 데이터를 쓰는 중 오류 발생: " + e.toString());
    }
  }
}
