package com.eomcs.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM BOARD WHERE BNO=?")) { 
      stmt.setInt(1, no);
      return stmt.executeUpdate();
    }
  }

  public int update(Board board) throws Exception {
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
            "UPDATE BOARD SET CONT=? WHERE BNO=?")) {
      stmt.setString(1, board.getContents());
      stmt.setInt(2, board.getNo());
      return stmt.executeUpdate();
    }
  }

  public int add(Board board) throws Exception {
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
            "INSERT INTO BOARD(CONT,MNO,LNO) VALUES(?,?,?)")) {
      stmt.setString(1, board.getContents());
      stmt.setInt(2, board.getWriterNo());
      stmt.setInt(3, board.getLessonNo());
      return stmt.executeUpdate();
    }
  }

  public List<Board> list() throws Exception {
    ArrayList<Board> boards = new ArrayList<>();

    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT BNO,CONT,CDT,VIEW,MNO,LNO FROM BOARD");
        ResultSet rs = stmt.executeQuery()) {

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
        PreparedStatement stmt = connection.prepareStatement("SELECT BNO,CONT,CDT,VIEW,MNO,LNO FROM BOARD WHERE BNO=?")) {
     
      stmt.setInt(1, no);
      
      try (ResultSet rs = stmt.executeQuery()) {
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
}
