package com.eomcs.lms;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerTest {

  public static void main(String[] args) throws Exception {
    
    // 서버와 연결
    Socket socket = new Socket("localhost", 8888);
    System.out.println("서버와 연결되었음.");
    
    PrintWriter out = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()));
    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    
    out.println("Hello!");
    out.flush();
    
    System.out.println(in.readLine());
    
    in.close();
    out.close();
    socket.close();
    System.out.println("서버와 연결을 끊었음.");

  }

}
