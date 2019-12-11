package com.eomcs.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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
        Statement stmt = connection.createStatement()) { 
      return stmt.executeUpdate("DELETE FROM LESSON WHERE LNO=" + no);
    }
  }

  public int update(Lesson lesson) throws Exception {
    try (Connection connection = dataSource.getConnection();
        Statement stmt = connection.createStatement()) {
      return stmt.executeUpdate("UPDATE LESSON SET"
          + " TITLE='" + lesson.getTitle() + "',"
          + " CONT='" + lesson.getContents() + "',"
          + " SDT='" + lesson.getStartDate() + "',"
          + " EDT='" + lesson.getEndDate() + "',"
          + " TOT_HR=" + lesson.getTotalHours() + ","
          + " DAY_HR=" + lesson.getDayHours() + "," 
          + " MNO=" + lesson.getOwnerNo()
          + " WHERE LNO=" + lesson.getNo());
    }
  }

  public int add(Lesson lesson) throws Exception {
    try (Connection connection = dataSource.getConnection();
        Statement stmt = connection.createStatement()) {
      return stmt.executeUpdate("INSERT INTO LESSON(TITLE,CONT,SDT,EDT,TOT_HR,DAY_HR,MNO)"
          + " VALUES('" + lesson.getTitle()
          + "','" + lesson.getContents()
          + "','" + lesson.getStartDate()
          + "','" + lesson.getEndDate()
          + "'," + lesson.getTotalHours()
          + "," + lesson.getDayHours()
          + "," + lesson.getOwnerNo() + ")");
    }
  }

  public List<Lesson> list() throws Exception {
    ArrayList<Lesson> lessons = new ArrayList<>();

    try (Connection connection = dataSource.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT LNO,TITLE,SDT,EDT,TOT_HR FROM LESSON")) {
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
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(
            "SELECT LNO,TITLE,CONT,SDT,EDT,TOT_HR,DAY_HR,MNO FROM LESSON WHERE LNO=" + no)) {

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
