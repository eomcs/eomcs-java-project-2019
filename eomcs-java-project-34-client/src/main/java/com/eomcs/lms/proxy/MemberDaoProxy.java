package com.eomcs.lms.proxy;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Date;
import com.eomcs.lms.domain.Member;

public class MemberDaoProxy {

  String host;
  int port;

  public MemberDaoProxy(String host, int port) {
    this.host = host;
    this.port = port;
  }

  public int delete(int no) throws Exception {
    try (Socket socket = new Socket(host, port);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream()); 
        ) {
      out.writeUTF("/member/delete");
      out.writeInt(no);
      out.flush();

      if (in.readUTF().equals("ok")) {
        return 1;
      } else {
        return 0;
      }    
    }
  }

  public int update(Member member) throws Exception {
    try (Socket socket = new Socket(host, port);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream()); 
        ) {
      out.writeUTF("/member/update");
      out.writeObject(member);
      out.flush();

      if (in.readUTF().equals("ok")) {
        return 1;
      } else {
        return 0;
      }
    }
  }

  public int add(Member member) throws Exception {
    try (Socket socket = new Socket(host, port);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream()); 
        ) {
      out.writeUTF("/member/add");
      out.writeObject(member);
      out.flush();

      if (in.readUTF().equals("ok")) {
        return 1;
      } else {
        return 0;
      }
    }
  }

  public Member[] list() throws Exception {
    try (Socket socket = new Socket(host, port);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream()); 
        ) {
      out.writeUTF("/member/list");
      out.flush();

      if (in.readUTF().equals("ok")) {
        return (Member[]) in.readObject();
      } else {
        return null;
      }
    }
  }

  public Member detail(int no) throws Exception {
    try (Socket socket = new Socket(host, port);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream()); 
        ) {
      out.writeUTF("/member/detail");
      out.writeInt(no);
      out.flush();

      if (in.readUTF().equals("ok")) {
        return (Member) in.readObject();
      } else {
        return null;
      }
    }
  }

  public static void main(String[] args) throws Exception {
    MemberDaoProxy memberDao = new MemberDaoProxy("localhost", 8888);

    Member member = new Member();
    member.setNo(3);
    member.setName("유관순");
    member.setEmail("yoo@test.com");
    member.setPassword("1111");
    member.setPhoto("yoo.jpeg");
    member.setTel("1111-2222");
    member.setRegisteredDate(new Date(System.currentTimeMillis()));

    memberDao.add(member);

    member = new Member();
    member.setNo(3);
    member.setName("유관순x");
    member.setEmail("yoo@test.comx");
    member.setPassword("1111x");
    member.setPhoto("yoo.jpegx");
    member.setTel("1111-2222x");
    member.setRegisteredDate(new Date(System.currentTimeMillis()));

    memberDao.update(member);

    System.out.println(memberDao.detail(3));

    memberDao.delete(3);

    Member[] members = memberDao.list();
    for (Member b : members) {
      System.out.println(b);
    }
  }
}
