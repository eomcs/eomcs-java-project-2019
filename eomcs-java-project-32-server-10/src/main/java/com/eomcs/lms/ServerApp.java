package com.eomcs.lms;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.dao.MemberDao;

public class ServerApp {

  public static void main(String[] args) throws Exception {
    LessonDao lessonDao = new LessonDao();
    MemberDao memberDao = new MemberDao();
    BoardDao boardDao = new BoardDao();
    
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
            memberDao.add(in, out);
            break;
          case "/member/detail":
            memberDao.detail(in, out);
            break;
          case "/member/list":
            memberDao.list(in, out);
            break;
          case "/member/update":
            memberDao.update(in, out);
            break;
          case "/member/delete":
            memberDao.delete(in, out);
            break;
          case "/lesson/add":
            lessonDao.add(in, out);
            break;
          case "/lesson/detail":
            lessonDao.detail(in, out);
            break;
          case "/lesson/list":
            lessonDao.list(in, out);
            break;
          case "/lesson/update":
            lessonDao.update(in, out);
            break;
          case "/lesson/delete":
            lessonDao.delete(in, out);
            break; 
          case "/board/add":
            boardDao.add(in, out);
            break;
          case "/board/detail":
            boardDao.detail(in, out);
            break;
          case "/board/list":
            boardDao.list(in, out);
            break;
          case "/board/update":
            boardDao.update(in, out);
            break;
          case "/board/delete":
            boardDao.delete(in, out);
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
