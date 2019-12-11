package com.eomcs.lms;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;
import com.eomcs.context.ApplicationContextListener;
import com.eomcs.lms.proxy.BoardDaoProxy;
import com.eomcs.lms.proxy.LessonDaoProxy;
import com.eomcs.lms.proxy.MemberDaoProxy;

public class DataLoaderListener implements ApplicationContextListener {

  Socket socket;
  ObjectOutputStream out;
  ObjectInputStream in;
  
  @Override
  public void contextInitialized(Map<String,Object> context) {
    System.out.println("DataLoaderListener.contextInitialized() 실행!");

    try {
      socket = new Socket("localhost", 8888);
      out = new ObjectOutputStream(socket.getOutputStream());
      in = new ObjectInputStream(socket.getInputStream()); 

      context.put("lessonDao", new LessonDaoProxy(in, out));
      context.put("memberDao", new MemberDaoProxy(in, out));
      context.put("boardDao", new BoardDaoProxy(in, out));
      
    } catch (Exception e) {
      System.out.println("서버 연결 오류!");
    }
  }

  @Override
  public void contextDestroyed(Map<String,Object> context) {
    System.out.println("DataLoaderListener.contextInitialized() 실행!");

    try {
      out.writeUTF("quit");
      out.flush();
    } catch (Exception e) {
      // 종료할 때 발생하는 오류는 처리하지 않는다.
    } finally {
      try {out.close();} catch (Exception e) {}
      try {in.close();} catch (Exception e) {}
      try {socket.close();} catch (Exception e) {}
    }
  }
}
