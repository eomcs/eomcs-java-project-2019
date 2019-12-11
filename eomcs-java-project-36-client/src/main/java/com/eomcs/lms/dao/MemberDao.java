package com.eomcs.lms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.eomcs.lms.domain.Member;

public class MemberDao {

  Connection connection;

  public MemberDao(Connection connection) {
    this.connection = connection;
  }

  public int delete(int no) throws Exception {
    try (Statement stmt = connection.createStatement()) { 
      return stmt.executeUpdate("DELETE FROM MEMBER WHERE MNO=" + no);
    }
  }

  public int update(Member member) throws Exception {
    try (Statement stmt = connection.createStatement()) {
      return stmt.executeUpdate("UPDATE MEMBER SET"
          + " NAME='" + member.getName() + "',"
          + " EMAIL='" + member.getEmail() + "',"
          + " PWD='" + member.getPassword() + "',"
          + " PHOTO='" + member.getPhoto() + "',"
          + " TEL='" + member.getTel() + "'"
          + " WHERE MNO=" + member.getNo());
    }
  }

  public int add(Member member) throws Exception {
    try (Statement stmt = connection.createStatement()) {
      return stmt.executeUpdate("INSERT INTO MEMBER(NAME,EMAIL,PWD,PHOTO,TEL)"
          + " VALUES('" + member.getName()
          + "','" + member.getEmail()
          + "','" + member.getPassword()
          + "','" + member.getPhoto()
          + "','" + member.getTel() + "')");
    }
  }

  public List<Member> list() throws Exception {
    ArrayList<Member> members = new ArrayList<>();

    try (Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT MNO,NAME,EMAIL,TEL,CDT FROM MEMBER")) {
      while (rs.next()) {
        Member member = new Member();
        member.setNo(rs.getInt("MNO"));
        member.setName(rs.getString("NAME"));
        member.setEmail(rs.getString("EMAIL"));
        member.setTel(rs.getString("TEL"));
        member.setRegisteredDate(rs.getDate("CDT"));

        members.add(member);
      }
    }

    return members; 
  }

  public Member detail(int no) throws Exception {
    try (Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(
            "SELECT MNO,NAME,EMAIL,PWD,PHOTO,TEL,CDT FROM MEMBER WHERE MNO=" + no)) {

      if (rs.next()) {
        Member member = new Member();
        member.setNo(rs.getInt("MNO"));
        member.setName(rs.getString("NAME"));
        member.setEmail(rs.getString("EMAIL"));
        member.setPassword(rs.getString("PWD"));
        member.setPhoto(rs.getString("PHOTO"));
        member.setTel(rs.getString("TEL"));
        member.setRegisteredDate(rs.getDate("CDT"));

        return member;

      } else {
        return null;
      }
    }
  }

  public static void main(String[] args) throws Exception {
    try (Connection connection = DriverManager.getConnection(
        "jdbc:mariadb://localhost:3306/eomcs?user=eomcs&password=1111")) {
      MemberDao memberDao = new MemberDao(connection);
      
      /*
      Member member = new Member();
      member.setName("유관순2");
      member.setEmail("yoo2@test.com");
      member.setPassword("1112");
      member.setPhoto("yoo2.jpeg");
      member.setTel("1111-2222");

      memberDao.add(member);
      */
      
      /*
      System.out.println(memberDao.detail(12));
      
      
      Member member = new Member();
      member.setNo(12);
      member.setName("유관순2xx");
      member.setEmail("yoo2xxx@test.com");
      member.setPassword("1112xxx");
      member.setPhoto("yoo2.jpegxxx");
      member.setTel("1111-2222xxx");

      memberDao.update(member);

      System.out.println(memberDao.detail(12));
      */
      
      //memberDao.delete(12);
      
      List<Member> members = memberDao.list();
      for (Member b : members) {
        System.out.println(b);
      }
    }
  }
}
