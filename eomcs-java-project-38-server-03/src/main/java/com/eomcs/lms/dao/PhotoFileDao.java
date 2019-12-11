package com.eomcs.lms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.eomcs.lms.domain.PhotoFile;

public class PhotoFileDao {

  Connection connection;

  public PhotoFileDao(Connection connection) {
    this.connection = connection;
  }

  public int delete(int no) throws Exception {
    try (Statement stmt = connection.createStatement()) { 
      return stmt.executeUpdate("DELETE FROM PHO_FILE WHERE PFNO=" + no);
    }
  }
  
  public int deleteByBoard(int boardNo) throws Exception {
    try (Statement stmt = connection.createStatement()) { 
      return stmt.executeUpdate("DELETE FROM PHO_FILE WHERE PNO=" + boardNo);
    }
  }

  public int add(PhotoFile photoFile) throws Exception {
    try (Statement stmt = connection.createStatement()) {
      return stmt.executeUpdate("INSERT INTO PHO_FILE(PATH,PNO)"
          + " VALUES('" + photoFile.getFilepath()
          + "'," + photoFile.getBoardNo() + ")");
    }
  }

  public List<PhotoFile> list() throws Exception {
    ArrayList<PhotoFile> photoFiles = new ArrayList<>();

    try (Statement stmt = connection.createStatement();
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

    try (Statement stmt = connection.createStatement();
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
  
  public static void main(String[] args) throws Exception {
    try (Connection connection = DriverManager.getConnection(
        "jdbc:mariadb://localhost:3306/eomcs?user=eomcs&password=1111")) {
      PhotoFileDao photoFileDao = new PhotoFileDao(connection);

      /*
      PhotoFile photoFile = new PhotoFile();
      photoFile.setFilepath("p1.jpeg");
      photoFile.setBoardNo(1);
      
      photoFileDao.add(photoFile);
      
      photoFile = new PhotoFile();
      photoFile.setFilepath("p2.jpeg");
      photoFile.setBoardNo(1);
      
      photoFileDao.add(photoFile);
      */
      
      //photoFileDao.delete(11);
      
      List<PhotoFile> photoFiles = photoFileDao.list();
      for (PhotoFile f : photoFiles) {
        System.out.println(f);
      }
    }
  }
}
