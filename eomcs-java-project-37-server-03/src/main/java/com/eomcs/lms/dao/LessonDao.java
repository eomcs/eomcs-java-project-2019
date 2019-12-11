package com.eomcs.lms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.eomcs.lms.domain.Lesson;

public class LessonDao {

  Connection connection;

  public LessonDao(Connection connection) {
    this.connection = connection;
  }

  public int delete(int no) throws Exception {
    try (Statement stmt = connection.createStatement()) { 
      return stmt.executeUpdate("DELETE FROM LESSON WHERE LNO=" + no);
    }
  }

  public int update(Lesson lesson) throws Exception {
    try (Statement stmt = connection.createStatement()) {
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
    try (Statement stmt = connection.createStatement()) {
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

    try (Statement stmt = connection.createStatement();
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
    try (Statement stmt = connection.createStatement();
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

  public static void main(String[] args) throws Exception {
    try (Connection connection = DriverManager.getConnection(
        "jdbc:mariadb://localhost:3306/eomcs?user=eomcs&password=1111")) {
      
      LessonDao lessonDao = new LessonDao(connection);

      /*
      Lesson lesson = new Lesson();
      lesson.setTitle("자바 기초 과정");
      lesson.setContents("자바 기초 문법 강의입니다.");
      lesson.setStartDate(Date.valueOf("2019-1-1"));
      lesson.setEndDate(Date.valueOf("2019-2-15"));
      lesson.setTotalHours(80);
      lesson.setDayHours(8);
      lesson.setOwnerNo(1);

      lessonDao.add(lesson);
      
      lesson = new Lesson();
      lesson.setTitle("자바 기초 과정2");
      lesson.setContents("자바 기초 문법 강의입니다.2");
      lesson.setStartDate(Date.valueOf("2019-1-3"));
      lesson.setEndDate(Date.valueOf("2019-2-16"));
      lesson.setTotalHours(80);
      lesson.setDayHours(8);
      lesson.setOwnerNo(1);

      lessonDao.add(lesson);
      */
      
      /*
      System.out.println(lessonDao.detail(9));
      
      Lesson lesson = new Lesson();
      lesson.setNo(9);
      lesson.setTitle("자바 기초 과정x");
      lesson.setContents("자바 기초 문법 강의입니다.x");
      lesson.setStartDate(Date.valueOf("2019-1-3"));
      lesson.setEndDate(Date.valueOf("2019-2-18"));
      lesson.setTotalHours(81);
      lesson.setDayHours(9);
      lesson.setOwnerNo(1);

      lessonDao.update(lesson);
      
      
      System.out.println(lessonDao.detail(9));
      */
      
      lessonDao.delete(9);

      List<Lesson> lessons = lessonDao.list();
      for (Lesson b : lessons) {
        System.out.println(b);
      }
    }
  }
}
