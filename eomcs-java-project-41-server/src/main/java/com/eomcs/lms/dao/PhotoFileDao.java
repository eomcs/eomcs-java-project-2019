package com.eomcs.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.eomcs.lms.domain.PhotoFile;
import com.eomcs.sql.DataSource;

public class PhotoFileDao {

  DataSource dataSource;

  public PhotoFileDao(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public int delete(int no) throws Exception {
    try (Connection connection = dataSource.getConnection();
        Statement stmt = connection.createStatement()) { 
      return stmt.executeUpdate("DELETE FROM PHO_FILE WHERE PFNO=" + no);
    }
  }
  
  public int deleteByBoard(int boardNo) throws Exception {
    try (Connection connection = dataSource.getConnection();
        Statement stmt = connection.createStatement()) { 
      return stmt.executeUpdate("DELETE FROM PHO_FILE WHERE PNO=" + boardNo);
    }
  }

  public int add(PhotoFile photoFile) throws Exception {
    try (Connection connection = dataSource.getConnection();
        Statement stmt = connection.createStatement()) {
      return stmt.executeUpdate("INSERT INTO PHO_FILE(PATH,PNO)"
          + " VALUES('" + photoFile.getFilepath()
          + "'," + photoFile.getBoardNo() + ")");
    }
  }

  public List<PhotoFile> list() throws Exception {
    ArrayList<PhotoFile> photoFiles = new ArrayList<>();

    try (Connection connection = dataSource.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT PFNO,PATH,PNO FROM PHO_FILE")) {

      while (rs.next()) {
        PhotoFile photoFile = new PhotoFile();
        photoFile.setNo(rs.getInt("PFNO"));
        photoFile.setFilepath(rs.getString("PATH"));
        photoFile.setBoardNo(rs.getInt("PNO"));

        photoFiles.add(photoFile);
      }
    }

    return photoFiles; 
  }

  public List<PhotoFile> listByBoard(int boardNo) throws Exception {
    ArrayList<PhotoFile> photoFiles = new ArrayList<>();

    try (Connection connection = dataSource.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(
            "SELECT PFNO,PATH,PNO FROM PHO_FILE WHERE PNO=" + boardNo)) {

      while (rs.next()) {
        PhotoFile photoFile = new PhotoFile();
        photoFile.setNo(rs.getInt("PFNO"));
        photoFile.setFilepath(rs.getString("PATH"));
        photoFile.setBoardNo(rs.getInt("PNO"));

        photoFiles.add(photoFile);
      }
    }

    return photoFiles; 
  }
}
