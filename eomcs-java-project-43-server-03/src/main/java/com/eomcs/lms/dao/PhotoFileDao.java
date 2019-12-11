package com.eomcs.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
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
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM PHO_FILE WHERE PFNO=?")) { 
      stmt.setInt(1, no);
      return stmt.executeUpdate();
    }
  }

  public int deleteByBoard(int boardNo) throws Exception {
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM PHO_FILE WHERE PNO=?")) {
      stmt.setInt(1, boardNo);
      return stmt.executeUpdate();
    }
  }

  public int add(PhotoFile photoFile) throws Exception {
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
            "INSERT INTO PHO_FILE(PATH,PNO) VALUES(?,?)")) {
      stmt.setString(1, photoFile.getFilepath());
      stmt.setInt(2, photoFile.getBoardNo());
      return stmt.executeUpdate();
    }
  }

  public List<PhotoFile> list() throws Exception {
    ArrayList<PhotoFile> photoFiles = new ArrayList<>();

    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT PFNO,PATH,PNO FROM PHO_FILE");
        ResultSet rs = stmt.executeQuery()) {

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
        PreparedStatement stmt = connection.prepareStatement(
            "SELECT PFNO,PATH,PNO FROM PHO_FILE WHERE PNO=?")) {

      stmt.setInt(1, boardNo);
      
      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          PhotoFile photoFile = new PhotoFile();
          photoFile.setNo(rs.getInt("PFNO"));
          photoFile.setFilepath(rs.getString("PATH"));
          photoFile.setBoardNo(rs.getInt("PNO"));

          photoFiles.add(photoFile);
        }
      }
    }

    return photoFiles; 
  }
}
