package com.eomcs.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.eomcs.lms.domain.Board;
import com.eomcs.sql.DataSource;

public class BoardDao {

  DataSource dataSource;

  public BoardDao(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public int delete(int no) throws Exception {
    try (Connection connection = dataSource.getConnection();
        Statement stmt = connection.createStatement()) { 
      return stmt.executeUpdate("DELETE FROM BOARD WHERE BNO=" + no);
    }
  }

  public int update(Board board) throws Exception {
    try (Connection connection = dataSource.getConnection();
        Statement stmt = connection.createStatement()) {
      return stmt.executeUpdate("UPDATE BOARD SET"
          + " CONT='" + board.getContents() + "'"
          + " WHERE BNO=" + board.getNo());
    }
  }

  public int add(Board board) throws Exception {
    try (Connection connection = dataSource.getConnection();
        Statement stmt = connection.createStatement()) {
      return stmt.executeUpdate("INSERT INTO BOARD(CONT,MNO,LNO)"
          + " VALUES('" + board.getContents()
          + "'," + board.getWriterNo()
          + "," + board.getLessonNo() + ")");
    }
  }

  public List<Board> list() throws Exception {
    ArrayList<Board> boards = new ArrayList<>();

    try (Connection connection = dataSource.getConnection();
        Statement stmt = connection.createStatement();
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
    try (Connection connection = dataSource.getConnection();
        Statement stmt = connection.createStatement();
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
}
