package com.eomcs.lms;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import com.eomcs.lms.domain.Board;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.lms.domain.Member;

public class ServerApp {
  static ArrayList<Lesson> lessons = new ArrayList<>();
  static ArrayList<Member> members = new ArrayList<>();
  static ArrayList<Board> boards = new ArrayList<>();

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
            addMember(in, out);
            break;
          case "/member/detail":
            detailMember(in, out);
            break;
          case "/member/list":
            listMembers(in, out);
            break;
          case "/member/update":
            updateMember(in, out);
            break;
          case "/member/delete":
            deleteMember(in, out);
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

  private static void deleteMember(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    int no = in.readInt();
    int index = indexOfMember(no);
    
    if (index == -1) {
      out.writeUTF("fail");
    } else { 
      members.remove(index);
      out.writeUTF("ok");
    }
    out.flush();
  }

  private static void updateMember(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    Member member = (Member) in.readObject();
    
    int index = indexOfMember(member.getNo());
    
    if (index == -1) {
      out.writeUTF("fail");
    } else { 
      members.set(index, member);
      out.writeUTF("ok");
    }
    out.flush();
  }

  private static void listMembers(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    out.writeUTF("ok");
    Member[] arr = new Member[members.size()];
    out.writeObject(members.toArray(arr));
    out.flush();
  }

  private static void detailMember(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    int no = in.readInt();
    int index = indexOfMember(no);
    
    if (index == -1) {
      out.writeUTF("fail");
    } else { 
      out.writeUTF("ok");
      out.writeObject(members.get(index));
    }
    out.flush();
  }

  private static void addMember(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    members.add((Member)in.readObject());
    
    out.writeUTF("ok");
    out.flush();
  }
  
  private static int indexOfMember(int no) {
    for (int i = 0; i < members.size(); i++) {
      Member m = members.get(i);
      if (m.getNo() == no)
        return i;
    }
    return -1;
  }
  
}
