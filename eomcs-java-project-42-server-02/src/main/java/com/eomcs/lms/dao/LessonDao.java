package com.eomcs.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.sql.DataSource;

public class LessonDao {

  DataSource dataSource;

  public LessonDao(DataSource dataSource) {
    this.dataSource = dataSource;
  }


  public int delete(int no) throws Exception {
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM LESSON WHERE LNO=?")) { 
      stmt.setInt(1, no);
      return stmt.executeUpdate();
    }
  }

  public int update(Lesson lesson) throws Exception {
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
            "UPDATE LESSON SET"
                + " TITLE=?, CONT=?, SDT=?, EDT=?, TOT_HR=?, DAY_HR=?, MNO=? WHERE LNO=?")) {
      stmt.setString(1, lesson.getTitle());
      stmt.setString(2, lesson.getContents());
      stmt.setDate(3, lesson.getStartDate());
      stmt.setDate(4, lesson.getEndDate());
      stmt.setInt(5, lesson.getTotalHours());
      stmt.setInt(6, lesson.getDayHours());
      stmt.setInt(7, lesson.getOwnerNo());
      stmt.setInt(8, lesson.getNo());
      return stmt.executeUpdate();
    }
  }

  public int add(Lesson lesson) throws Exception {
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
            "INSERT INTO LESSON(TITLE,CONT,SDT,EDT,TOT_HR,DAY_HR,MNO)"
                + " VALUES(?,?,?,?,?,?,?)")) {
      stmt.setString(1, lesson.getTitle());
      stmt.setString(2, lesson.getContents());
      stmt.setDate(3, lesson.getStartDate());
      stmt.setDate(4, lesson.getEndDate());
      stmt.setInt(5, lesson.getTotalHours());
      stmt.setInt(6, lesson.getDayHours());
      stmt.setInt(7, lesson.getOwnerNo());
      return stmt.executeUpdate();
    }
  }

  public List<Lesson> list() throws Exception {
    ArrayList<Lesson> lessons = new ArrayList<>();

    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
            "SELECT LNO,TITLE,SDT,EDT,TOT_HR FROM LESSON");
        ResultSet rs = stmt.executeQuery()) {
      while (rs.next()) {
        Lesson lesson = new Lesson();
        lesson.setNo(rs.getInt("LNO"));
        lesson.setTitle(rs.getString("TITLE"));
        lesson.setStartDate(rs.getDate("SDT"));
        lesson.setEndDate(rs.getDate("EDT"));
        lesson.setTotalHours(rs.getInt("TOT_HR"));

        lessons.add(lesson);
      }
    }

    return lessons; 
  }

  public Lesson detail(int no) throws Exception {
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
            "SELECT LNO,TITLE,CONT,SDT,EDT,TOT_HR,DAY_HR,MNO FROM LESSON WHERE LNO=?")) {
      
      stmt.setInt(1, no);
      
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          Lesson lesson = new Lesson();
          lesson.setNo(rs.getInt("LNO"));
          lesson.setTitle(rs.getString("TITLE"));
          lesson.setContents(rs.getString("CONT"));
          lesson.setStartDate(rs.getDate("SDT"));
          lesson.setEndDate(rs.getDate("EDT"));
          lesson.setTotalHours(rs.getInt("TOT_HR"));
          lesson.setDayHours(rs.getInt("DAY_HR"));
          lesson.setOwnerNo(rs.getInt("MNO"));
          return lesson;

        } else {
          return null;
        }
      }
    }

  }
}
