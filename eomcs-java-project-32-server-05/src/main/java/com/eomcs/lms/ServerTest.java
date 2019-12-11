package com.eomcs.lms;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
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

    add(m);

    m = new Member();
    m.setNo(2);
    m.setName("임꺽정");
    m.setEmail("leem@test.com");
    m.setPassword("1111");
    m.setPhoto("leem.jpeg");
    m.setTel("1111-3333");

    add(m);

    list();

    quit();

    in.close();
    out.close();
    socket.close();
    System.out.println("서버와 연결을 끊었음.");

  }

  private static void add(Object obj) throws Exception {

    out.writeUTF("add");
    out.writeObject(obj);
    out.flush();

    System.out.println(in.readUTF());

  }

  private static void list() throws Exception {

    out.writeUTF("list");
    out.flush();

    @SuppressWarnings("unchecked")
    List<Member> list = (List<Member>) in.readObject();

    for (Member m : list) {
      System.out.println(m);
    }

  }

  private static void quit() throws Exception {
    out.writeUTF("quit");
    out.flush();
  }

}
