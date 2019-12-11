package com.eomcs.lms;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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

    Member m = new Member();
    m.setNo(1);
    m.setName("홍길동");
    m.setEmail("hong@test.com");
    m.setPassword("1111");
    m.setPhoto("hong.jpeg");
    m.setTel("1111-2222");

    System.out.println("/member/add 요청 -----------------------");
    add(m);
    
    m = new Member();
    m.setNo(2);
    m.setName("임꺽정");
    m.setEmail("leem@test.com");
    m.setPassword("1111");
    m.setPhoto("leem.jpeg");
    m.setTel("1111-3333");

    System.out.println("/member/add 요청 -----------------------");
    add(m);
    
    System.out.println("/member/detail 요청 -----------------------");
    detail();
    
    m = new Member();
    m.setNo(1);
    m.setName("홍길동x");
    m.setEmail("hongx@test.com");
    m.setPassword("1112");
    m.setPhoto("hongx.jpeg");
    m.setTel("1111-2223");
    
    System.out.println("/member/update 요청 -----------------------");
    update(m);

    System.out.println("/member/detail 요청 -----------------------");
    detail();

    System.out.println("/member/delete 요청 -----------------------");
    delete();

    System.out.println("/member/list 요청 -----------------------");
    list();

    quit();
    
    in.close();
    out.close();
    socket.close();
    System.out.println("서버와 연결을 끊었음.");

  }

  private static void delete() throws Exception {
    out.writeUTF("/member/delete");
    out.writeInt(1);
    out.flush();
    
    String status = in.readUTF();
    
    if (!status.equals("ok")) {
      System.out.println("작업 실패!");
      return;
    }
    
    System.out.println("작업 성공!");
  }

  private static void update(Object obj) throws Exception {
    out.writeUTF("/member/update");
    out.writeObject(obj);
    out.flush();

    String status = in.readUTF();
    
    if (!status.equals("ok")) {
      System.out.println("작업 실패!");
      return;
    }
    
    System.out.println("작업 성공!");
  }

  private static void add(Object obj) throws Exception {
    out.writeUTF("/member/add");
    out.writeObject(obj);
    out.flush();

    System.out.println(in.readUTF());
  }
  
  private static void list() throws Exception {
    out.writeUTF("/member/list");
    out.flush();

    String status = in.readUTF();
    
    if (!status.equals("ok")) {
      System.out.println("작업 실패!");
      return;
    }
    
    //@SuppressWarnings("unchecked")
    //List<Member> list = (List<Member>) in.readObject();
    Member[] list = (Member[]) in.readObject();
    
    for (Member m : list) {
      System.out.println(m);
    }
  }
  
  private static void detail() throws Exception {
    out.writeUTF("/member/detail");
    out.writeInt(1);
    out.flush();
    
    String status = in.readUTF();
    
    if (!status.equals("ok")) {
      System.out.println("작업 실패!");
      return;
    } 
    
    Member m = (Member) in.readObject();
    System.out.println(m);
  }
  
  
  
  private static void quit() throws Exception {
    out.writeUTF("quit");
    out.flush();
  }

}
