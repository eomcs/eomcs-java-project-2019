package com.eomcs.lms.proxy;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Date;
import com.eomcs.lms.domain.Board;

public class BoardDaoProxy {

  String host;
  int port;

  public BoardDaoProxy(String host, int port) {
    this.host = host;
    this.port = port;
  }

  public int delete(int no) throws Exception {
    try (Socket socket = new Socket(host, port);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream()); 
        ) {

      out.writeUTF("/board/delete");
      out.writeInt(no);
      out.flush();

      if (in.readUTF().equals("ok")) {
        return 1;
      } else {
        return 0;
      }
    }
  }

  public int update(Board board) throws Exception {
    try (Socket socket = new Socket(host, port);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream()); 
        ) {
      out.writeUTF("/board/update");
      out.writeObject(board);
      out.flush();

      if (in.readUTF().equals("ok")) {
        return 1;
      } else {
        return 0;
      }
    }
  }

  public int add(Board board) throws Exception {
    try (Socket socket = new Socket(host, port);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream()); 
        ) {
      out.writeUTF("/board/add");
      out.writeObject(board);
      out.flush();

      if (in.readUTF().equals("ok")) {
        return 1;
      } else {
        return 0;
      }
    }
  }

  public Board[] list() throws Exception {
    try (Socket socket = new Socket(host, port);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream()); 
        ) {
      out.writeUTF("/board/list");
      out.flush();

      if (in.readUTF().equals("ok")) {
        return (Board[]) in.readObject();
      } else {
        return null;
      }
    }
  }

  public Board detail(int no) throws Exception {
    try (Socket socket = new Socket(host, port);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream()); 
        ) {
      out.writeUTF("/board/detail");
      out.writeInt(no);
      out.flush();

      if (in.readUTF().equals("ok")) {
        return (Board) in.readObject();
      } else {
        return null;
      }
    }
  }

  public static void main(String[] args) throws Exception {
    BoardDaoProxy boardDao = new BoardDaoProxy("localhost", 8888);


    Board board = new Board();
    board.setNo(3);
    board.setContents("내용입니다.");
    board.setViewCount(0);
    board.setCreatedDate(new Date(System.currentTimeMillis()));

    boardDao.add(board);



    board = new Board();
    board.setNo(3);
    board.setContents("내용입니다.x");
    board.setViewCount(10);
    board.setCreatedDate(new Date(System.currentTimeMillis()));

    boardDao.update(board);


    System.out.println(boardDao.detail(3));

    boardDao.delete(3);

    Board[] boards = boardDao.list();
    for (Board b : boards) {
      System.out.println(b);
    }
  }
}
