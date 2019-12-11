package com.eomcs.lms;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import com.eomcs.lms.domain.Member;

public class ServerApp {

  public static void main(String[] args) throws Exception {
    
    // 서버 소켓 생성
    ServerSocket serverSocket = new ServerSocket(8888);
    System.out.println("서버 시작!");
    
    while (true) {
      // 클라이언트 연결을 기다림
      Socket socket = serverSocket.accept();
      System.out.println("클라이언트와 연결되었음.");
      
      ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
      ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
      
      Member m = (Member) in.readObject();
      System.out.println(m);
      
      out.writeUTF("객체를 받았습니다.");
      out.flush();
      
      in.close();
      out.close();
      socket.close();
      System.out.println("클라이언트와 연결을 끊었음.");
    }
  }

}
