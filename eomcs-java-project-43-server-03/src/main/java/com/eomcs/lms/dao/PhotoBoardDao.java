package com.eomcs.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
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
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM PHOTO WHERE PNO=?")) {
      stmt.setInt(1, no);
      return stmt.executeUpdate();
    }
  }

  public int update(PhotoBoard photo) throws Exception {
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
            "UPDATE PHOTO SET TITLE=? WHERE PNO=?")) {
      stmt.setString(1, photo.getTitle());
      stmt.setInt(2, photo.getNo());
      return stmt.executeUpdate();
    }
  }

  public int add(PhotoBoard photo) throws Exception {
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
            "INSERT INTO PHOTO(TITLE,LNO) VALUES(?,?)", 
            PreparedStatement.RETURN_GENERATED_KEYS)) {

      stmt.setString(1, photo.getTitle());
      stmt.setInt(2, photo.getLessonNo());

      int count = stmt.executeUpdate();

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
        PreparedStatement stmt = connection.prepareStatement(
            "SELECT PNO,TITLE,CDT,VIEW,LNO FROM PHOTO");
        ResultSet rs = stmt.executeQuery()) {

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
        PreparedStatement stmt = connection.prepareStatement(
            "SELECT PNO,TITLE,CDT,VIEW,LNO FROM PHOTO WHERE PNO=?")) {
      
      stmt.setInt(1, no);
      
      try (ResultSet rs = stmt.executeQuery()) {
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
}
