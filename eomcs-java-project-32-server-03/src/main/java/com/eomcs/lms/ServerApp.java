package com.eomcs.lms;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
      
      PrintWriter out = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()));
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      
      String message = in.readLine();
      
      out.println(message);
      out.flush();
      
      in.close();
      out.close();
      socket.close();
      System.out.println("클라이언트와 연결을 끊었음.");
    }
  }

}
