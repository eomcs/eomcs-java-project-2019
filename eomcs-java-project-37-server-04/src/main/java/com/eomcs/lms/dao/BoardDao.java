package com.eomcs.lms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.eomcs.lms.domain.Board;

public class BoardDao {

  Connection connection;

  public BoardDao(Connection connection) {
    this.connection = connection;
  }

  public int delete(int no) throws Exception {
    try (Statement stmt = connection.createStatement()) { 
      return stmt.executeUpdate("DELETE FROM BOARD WHERE BNO=" + no);
    }
  }

  public int update(Board board) throws Exception {
    try (Statement stmt = connection.createStatement()) {
      return stmt.executeUpdate("UPDATE BOARD SET"
          + " CONT='" + board.getContents() + "'"
          + " WHERE BNO=" + board.getNo());
    }
  }

  public int add(Board board) throws Exception {
    try (Statement stmt = connection.createStatement()) {
      return stmt.executeUpdate("INSERT INTO BOARD(CONT,MNO,LNO)"
          + " VALUES('" + board.getContents()
          + "'," + board.getWriterNo()
          + "," + board.getLessonNo() + ")");
    }
  }

  public List<Board> list() throws Exception {
    ArrayList<Board> boards = new ArrayList<>();

    try (Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT BNO,CONT,CDT,VIEW,MNO,LNO FROM BOARD")) {

      while (rs.next()) {
        Board board = new Board();
        board.setNo(rs.getInt("BNO"));
        board.setContents(rs.getString("CONT"));
        board.setCreatedDate(rs.getDate("CDT"));
        board.setViewCount(rs.getInt("VIEW"));
        board.setWriterNo(rs.getInt("MNO"));
        board.setLessonNo(rs.getInt("LNO"));

        boards.add(board);
      }
    }

    return boards; 
  }

  public Board detail(int no) throws Exception {
    try (Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(
            "SELECT BNO,CONT,CDT,VIEW,MNO,LNO FROM BOARD WHERE BNO=" + no)) {

      if (rs.next()) {
        Board board = new Board();
        board.setNo(rs.getInt("BNO"));
        board.setContents(rs.getString("CONT"));
        board.setCreatedDate(rs.getDate("CDT"));
        board.setViewCount(rs.getInt("VIEW"));
        board.setWriterNo(rs.getInt("MNO"));
        board.setLessonNo(rs.getInt("LNO"));

        return board;

      } else {
        return null;
      }
    }
  }

  public static void main(String[] args) throws Exception {
    try (Connection connection = DriverManager.getConnection(
        "jdbc:mariadb://localhost:3306/eomcs?user=eomcs&password=1111")) {
      BoardDao boardDao = new BoardDao(connection);

      /*
      Board board = new Board();
      board.setContents("내용입니다.");
      board.setViewCount(0);
      board.setCreatedDate(new Date(System.currentTimeMillis()));
      board.setWriterNo(1);
      board.setLessonNo(1);
      
      boardDao.add(board);
      */

      /*
      System.out.println(boardDao.detail(11));
      
      Board board = new Board();
      board.setNo(11);
      board.setContents("내용입니다.x");
      board.setViewCount(100);
      board.setCreatedDate(new Date(System.currentTimeMillis()));
      board.setWriterNo(2);
      board.setLessonNo(3);
      
      boardDao.update(board);

      
      System.out.println(boardDao.detail(11));
      */
      
      //boardDao.delete(11);
      
      List<Board> boards = boardDao.list();
      for (Board b : boards) {
        System.out.println(b);
      }
    }
  }
}
