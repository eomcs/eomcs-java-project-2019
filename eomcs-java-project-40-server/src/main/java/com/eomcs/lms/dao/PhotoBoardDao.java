package com.eomcs.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.eomcs.lms.domain.PhotoBoard;
import com.eomcs.sql.DataSource;

public class PhotoBoardDao {

  DataSource dataSource;

  public PhotoBoardDao(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public int delete(int no) throws Exception {
    try (Connection connection = dataSource.getConnection();
        Statement stmt = connection.createStatement()) { 
      return stmt.executeUpdate("DELETE FROM PHOTO WHERE PNO=" + no);
    }
  }

  public int update(PhotoBoard photo) throws Exception {
    try (Connection connection = dataSource.getConnection();
        Statement stmt = connection.createStatement()) {
      return stmt.executeUpdate("UPDATE PHOTO SET"
          + " TITLE='" + photo.getTitle() + "'"
          + " WHERE PNO=" + photo.getNo());
    }
  }

  public int add(PhotoBoard photo) throws Exception {
    try (Connection connection = dataSource.getConnection();
        Statement stmt = connection.createStatement()) {
      int count = stmt.executeUpdate("INSERT INTO PHOTO(TITLE,LNO)"
          + " VALUES('" + photo.getTitle()
          + "'," + photo.getLessonNo() + ")", Statement.RETURN_GENERATED_KEYS);
      
      ResultSet rs = stmt.getGeneratedKeys();
      if (rs.next()) {
        photo.setNo(rs.getInt(1));
      }
      rs.close();
      
      return count;
    }
  }

  public List<PhotoBoard> list() throws Exception {
    ArrayList<PhotoBoard> photos = new ArrayList<>();

    try (Connection connection = dataSource.getConnection();
        Statement stmt = connection.createStatement();
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
    try (Connection connection = dataSource.getConnection();
        Statement stmt = connection.createStatement();
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
}
