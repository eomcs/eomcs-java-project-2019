package com.eomcs.lms;

import java.net.Socket;

public class ServerTest {

  public static void main(String[] args) throws Exception {
    
    // 서버와 연결
    Socket socket = new Socket("localhost", 8888);
    System.out.println("서버와 연결되었음.");
    
    socket.close();
    System.out.println("서버와 연결을 끊었음.");

  }

}
