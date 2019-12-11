package com.eomcs.lms;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {

  public static void main(String[] args) throws Exception {

    ServerSocket serverSocket = new ServerSocket(8888);
    System.out.println("서버 시작!");
    
    while (true) {
      // 클라이언트 연결을 기다림
      Socket socket = serverSocket.accept();
      System.out.println("클라이언트와 연결되었음.");
      
      ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
      ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
      
      loop1:
      while (true) {
        String command = in.readUTF();
        
        switch (command) {
          case "/member/add":
            MemberDao.add(in, out);
            break;
          case "/member/detail":
            MemberDao.detail(in, out);
            break;
          case "/member/list":
            MemberDao.list(in, out);
            break;
          case "/member/update":
            MemberDao.update(in, out);
            break;
          case "/member/delete":
            MemberDao.delete(in, out);
            break;
          case "/lesson/add":
            LessonDao.add(in, out);
            break;
          case "/lesson/detail":
            LessonDao.detail(in, out);
            break;
          case "/lesson/list":
            LessonDao.list(in, out);
            break;
          case "/lesson/update":
            LessonDao.update(in, out);
            break;
          case "/lesson/delete":
            LessonDao.delete(in, out);
            break; 
          case "/board/add":
            BoardDao.add(in, out);
            break;
          case "/board/detail":
            BoardDao.detail(in, out);
            break;
          case "/board/list":
            BoardDao.list(in, out);
            break;
          case "/board/update":
            BoardDao.update(in, out);
            break;
          case "/board/delete":
            BoardDao.delete(in, out);
            break;
          case "quit":
            break loop1;
          default:
            out.writeUTF("error");
            out.flush();
        }
      }
      
      in.close();
      out.close();
      socket.close();
      System.out.println("클라이언트와 연결을 끊었음.");
    } 
  }


 
  

}
