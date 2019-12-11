package com.eomcs.lms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.eomcs.lms.domain.PhotoBoard;

public class PhotoBoardDao {

  Connection connection;

  public PhotoBoardDao(Connection connection) {
    this.connection = connection;
  }

  public int delete(int no) throws Exception {
    try (Statement stmt = connection.createStatement()) { 
      return stmt.executeUpdate("DELETE FROM PHOTO WHERE PNO=" + no);
    }
  }

  public int update(PhotoBoard photo) throws Exception {
    try (Statement stmt = connection.createStatement()) {
      return stmt.executeUpdate("UPDATE PHOTO SET"
          + " TITLE='" + photo.getTitle() + "'"
          + " WHERE PNO=" + photo.getNo());
    }
  }

  public int add(PhotoBoard photo) throws Exception {
    try (Statement stmt = connection.createStatement()) {
      return stmt.executeUpdate("INSERT INTO PHOTO(TITLE,LNO)"
          + " VALUES('" + photo.getTitle()
          + "'," + photo.getLessonNo() + ")");
    }
  }

  public List<PhotoBoard> list() throws Exception {
    ArrayList<PhotoBoard> photos = new ArrayList<>();

    try (Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT PNO,TITLE,CDT,VIEW,LNO FROM PHOTO")) {

      while (rs.next()) {
        PhotoBoard photo = new PhotoBoard();
        photo.setNo(rs.getInt("PNO"));
        photo.setTitle(rs.getString("TITLE"));
        photo.setCreatedDate(rs.getDate("CDT"));
        photo.setViewCount(rs.getInt("VIEW"));
        photo.setLessonNo(rs.getInt("LNO"));

        photos.add(photo);
      }
    }

    return photos; 
  }

  public PhotoBoard detail(int no) throws Exception {
    try (Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(
            "SELECT PNO,TITLE,CDT,VIEW,LNO FROM PHOTO WHERE PNO=" + no)) {

      if (rs.next()) {
        PhotoBoard photo = new PhotoBoard();
        photo.setNo(rs.getInt("PNO"));
        photo.setTitle(rs.getString("TITLE"));
        photo.setCreatedDate(rs.getDate("CDT"));
        photo.setViewCount(rs.getInt("VIEW"));
        photo.setLessonNo(rs.getInt("LNO"));

        return photo;

      } else {
        return null;
      }
    }
  }

  public static void main(String[] args) throws Exception {
    try (Connection connection = DriverManager.getConnection(
        "jdbc:mariadb://localhost:3306/eomcs?user=eomcs&password=1111")) {
      PhotoBoardDao photoDao = new PhotoBoardDao(connection);

      /*
      PhotoBoard photo = new PhotoBoard();
      photo.setTitle("발표1");
      photo.setLessonNo(1);
      
      photoDao.add(photo);
      
      photo = new PhotoBoard();
      photo.setTitle("발표2");
      photo.setLessonNo(1);
      
      photoDao.add(photo);
      */
      
      /*
      System.out.println(photoDao.detail(5));
      
      PhotoBoard photo = new PhotoBoard();
      photo.setNo(5);
      photo.setTitle("발표1x");
      photo.setLessonNo(1);
      
      photoDao.update(photo);

      
      System.out.println(photoDao.detail(5));
      */
      
      //photoDao.delete(5);
      
      List<PhotoBoard> photos = photoDao.list();
      for (PhotoBoard b : photos) {
        System.out.println(b);
      }
    }
  }
}
