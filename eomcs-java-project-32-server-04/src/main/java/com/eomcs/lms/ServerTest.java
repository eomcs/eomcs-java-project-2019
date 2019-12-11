package com.eomcs.lms;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import com.eomcs.lms.domain.Member;

public class ServerTest {

  public static void main(String[] args) throws Exception {
    
    // 서버와 연결
    Socket socket = new Socket("localhost", 8888);
    System.out.println("서버와 연결되었음.");
    
    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
    
    Member m = new Member();
    m.setNo(1);
    m.setName("홍길동");
    m.setEmail("hong@test.com");
    m.setPassword("1111");
    m.setPhoto("hong.jpeg");
    m.setTel("1111-2222");
    
    out.writeObject(m);
    out.flush();
    
    System.out.println(in.readUTF());
    
    in.close();
    out.close();
    socket.close();
    System.out.println("서버와 연결을 끊었음.");

  }

}
