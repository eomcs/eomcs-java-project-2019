package com.eomcs.lms;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Date;
import com.eomcs.lms.domain.Board;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.lms.domain.Member;

public class ServerTest {

  static Socket socket;
  static ObjectOutputStream out;
  static ObjectInputStream in;

  public static void main(String[] args) throws Exception {

    socket = new Socket("localhost", 8888);
    System.out.println("서버와 연결되었음.");

    out = new ObjectOutputStream(socket.getOutputStream());
    in = new ObjectInputStream(socket.getInputStream());

    //testMemberAdd();
    //testLessonAdd();
    //testBoardAdd();
    
    testMemberList();
    testLessonList();
    testBoardList();
    
    //quit();
    //shutdown();
    
    in.close();
    out.close();
    socket.close();
    System.out.println("서버와 연결을 끊었음.");

  }
  
  private static void testMemberAdd() throws Exception {
    Member m = new Member();
    m.setNo(1);
    m.setName("홍길동");
    m.setEmail("hong@test.com");
    m.setPassword("1111");
    m.setPhoto("hong.jpeg");
    m.setTel("1111-2222");

    add("/member/add", m);
    
    m = new Member();
    m.setNo(2);
    m.setName("임꺽정");
    m.setEmail("leem@test.com");
    m.setPassword("1111");
    m.setPhoto("leem.jpeg");
    m.setTel("1111-3333");

    add("/member/add", m);
    
    m = new Member();
    m.setNo(1);
    m.setName("홍길동x");
    m.setEmail("hongx@test.com");
    m.setPassword("1112");
    m.setPhoto("hongx.jpeg");
    m.setTel("1111-2223");
    
    update("/member/update", m);
  }

  private static void testMemberList() throws Exception {
    list("/member/list");
  }
  
  private static void testLessonAdd() throws Exception {
    Lesson obj = new Lesson();
    obj.setNo(1);
    obj.setTitle("과정1");
    obj.setContents("과정설명1");
    obj.setStartDate(Date.valueOf("2019-1-1"));
    obj.setEndDate(Date.valueOf("2019-1-15"));
    obj.setTotalHours(1000);
    obj.setDayHours(8);
    
    add("/lesson/add", obj);
    
    obj = new Lesson();
    obj.setNo(2);
    obj.setTitle("과정2");
    obj.setContents("과정설명2");
    obj.setStartDate(Date.valueOf("2019-2-1"));
    obj.setEndDate(Date.valueOf("2019-2-15"));
    obj.setTotalHours(1000);
    obj.setDayHours(8);

    add("/lesson/add", obj);
    
    obj = new Lesson();
    obj.setNo(1);
    obj.setTitle("과정1x");
    obj.setContents("과정설명1x");
    obj.setStartDate(Date.valueOf("2019-1-2"));
    obj.setEndDate(Date.valueOf("2019-1-16"));
    obj.setTotalHours(1001);
    obj.setDayHours(9);
    
    update("/lesson/update", obj);
  }
  
  private static void testLessonList() throws Exception {
    list("/lesson/list");
  }
  
  private static void testBoardAdd() throws Exception {
    Board obj = new Board();
    obj.setNo(1);
    obj.setContents("내용1...");
    obj.setViewCount(1);
    
    add("/board/add", obj);
    
    obj = new Board();
    obj.setNo(2);
    obj.setContents("내용2...");
    obj.setViewCount(1);

    add("/board/add", obj);
    
    obj = new Board();
    obj.setNo(1);
    obj.setContents("내용1...xxx");
    obj.setViewCount(2);
    
    update("/board/update", obj);
  }

  private static void testBoardList() throws Exception {
    list("/board/list");
  }
  
  private static void delete(String command, int no) throws Exception {
    System.out.printf("[%s] -----------------\n", command);
    out.writeUTF(command);
    out.writeInt(no);
    out.flush();
    
    String status = in.readUTF();
    
    if (!status.equals("ok")) {
      System.out.println("작업 실패!");
      return;
    }
    
    System.out.println("작업 성공!");
  }

  private static void update(String command, Object obj) throws Exception {
    System.out.printf("[%s] -----------------\n", command);
    out.writeUTF(command);
    out.writeObject(obj);
    out.flush();

    String status = in.readUTF();
    
    if (!status.equals("ok")) {
      System.out.println("작업 실패!");
      return;
    }
    
    System.out.println("작업 성공!");
  }

  private static void add(String command, Object obj) throws Exception {
    System.out.printf("[%s] -----------------\n", command);
    out.writeUTF(command);
    out.writeObject(obj);
    out.flush();

    System.out.println(in.readUTF());
  }
  
  private static void list(String command) throws Exception {
    System.out.printf("[%s] -----------------\n", command);
    out.writeUTF(command);
    out.flush();

    String status = in.readUTF();
    
    if (!status.equals("ok")) {
      System.out.println("작업 실패!");
      return;
    }
    
    Object[] list = (Object[]) in.readObject();
    
    for (Object obj : list) {
      System.out.println(obj);
    }
  }
  
  private static void detail(String command, int no) throws Exception {
    System.out.printf("[%s] -----------------\n", command);
    out.writeUTF(command);
    out.writeInt(no);
    out.flush();
    
    String status = in.readUTF();
    
    if (!status.equals("ok")) {
      System.out.println("작업 실패!");
      return;
    } 
    
    Object obj = in.readObject();
    System.out.println(obj);
  }
  
  private static void quit() throws Exception {
    out.writeUTF("quit");
    out.flush();
  }
  
  private static void shutdown() throws Exception {
    out.writeUTF("shutdown");
    out.flush();
  }

}
