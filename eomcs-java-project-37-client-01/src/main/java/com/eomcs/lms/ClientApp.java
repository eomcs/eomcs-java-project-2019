package com.eomcs.lms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientApp {

  Scanner keyboard = new Scanner(System.in);

  String host;
  int port;

  public ClientApp(String host, int port) {
    this.host = host;
    this.port = port;
  }

  public void service() throws Exception {
    try (Socket socket = new Socket(host, port);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()))) {

      while (true) {
        String command = prompt();
        out.println(command);
        out.flush();

        if (command.equals("quit") || command.equals("shutdown"))
          break;

        while (true) {
          String responseText = in.readLine();
          
          if (responseText.equals("!end!")) {
            break;
          
          } else if (responseText.equals("!{}!")) {
            out.println(keyboard.nextLine());
            out.flush();
          
          } else {
            System.out.println(responseText);
          }
        }
      }
    }
  }

  private String prompt() {
    System.out.print("명령> ");
    return keyboard.nextLine();
  }

  public static void main(String[] args) throws Exception {
    ClientApp app = new ClientApp("localhost", 8888);

    app.service();
  }

}
