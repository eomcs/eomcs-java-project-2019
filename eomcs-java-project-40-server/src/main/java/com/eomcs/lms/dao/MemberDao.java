package com.eomcs.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.eomcs.lms.domain.Member;
import com.eomcs.sql.DataSource;

public class MemberDao {

  DataSource dataSource;

  public MemberDao(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public int delete(int no) throws Exception {
    try (Connection connection = dataSource.getConnection();
        Statement stmt = connection.createStatement()) { 
      return stmt.executeUpdate("DELETE FROM MEMBER WHERE MNO=" + no);
    }
  }

  public int update(Member member) throws Exception {
    try (Connection connection = dataSource.getConnection();
        Statement stmt = connection.createStatement()) {
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
    try (Connection connection = dataSource.getConnection();
        Statement stmt = connection.createStatement()) {
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

    try (Connection connection = dataSource.getConnection();
        Statement stmt = connection.createStatement();
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
  
  public List<Member> search(String keyword) throws Exception {
    ArrayList<Member> members = new ArrayList<>();

    try (Connection connection = dataSource.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT MNO,NAME,EMAIL,TEL,CDT FROM MEMBER"
            + " WHERE NAME LIKE '%" + keyword
            + "%' OR EMAIL LIKE '%" + keyword
            + "%' OR TEL LIKE '%" + keyword
            + "%'")) {
      
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
    try (Connection connection = dataSource.getConnection();
        Statement stmt = connection.createStatement();
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
}
