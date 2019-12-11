package com.eomcs.lms;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {

  public static void main(String[] args) throws Exception {
    
    // 서버 소켓 생성
    ServerSocket serverSocket = new ServerSocket(8888);
    System.out.println("서버 시작!");
    
    while (true) {
      // 클라이언트 연결을 기다림
      Socket socket = serverSocket.accept();
      System.out.println("클라이언트와 연결되었음.");
      
      socket.close();
      System.out.println("클라이언트와 연결을 끊었음.");
    }
  }

}
