package com.eomcs.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM MEMBER WHERE MNO=?")) { 
      stmt.setInt(1, no);
      return stmt.executeUpdate();
    }
  }

  public int update(Member member) throws Exception {
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
            "UPDATE MEMBER SET NAME=?, EMAIL=?, PWD=?, PHOTO=?, TEL=? WHERE MNO=?")) {

      stmt.setString(1, member.getName());
      stmt.setString(2, member.getEmail());
      stmt.setString(3, member.getPassword());
      stmt.setString(4, member.getPhoto());
      stmt.setString(5, member.getTel());
      stmt.setInt(6, member.getNo());

      return stmt.executeUpdate();
    }
  }

  public int add(Member member) throws Exception {
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
            "INSERT INTO MEMBER(NAME,EMAIL,PWD,PHOTO,TEL) VALUES(?,?,?,?,?)")) {

      stmt.setString(1, member.getName());
      stmt.setString(2, member.getEmail());
      stmt.setString(3, member.getPassword());
      stmt.setString(4, member.getPhoto());
      stmt.setString(5, member.getTel());

      return stmt.executeUpdate();
    }
  }

  public List<Member> list() throws Exception {
    ArrayList<Member> members = new ArrayList<>();

    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
            "SELECT MNO,NAME,EMAIL,TEL,CDT FROM MEMBER");
        ResultSet rs = stmt.executeQuery()) {
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
        PreparedStatement stmt = connection.prepareStatement(
            "SELECT MNO,NAME,EMAIL,TEL,CDT FROM MEMBER"
                + " WHERE NAME LIKE ? OR EMAIL LIKE ? OR TEL LIKE ?")) {

      stmt.setString(1, "%" + keyword + "%");
      stmt.setString(2, "%" + keyword + "%");
      stmt.setString(3, "%" + keyword + "%");

      try (ResultSet rs = stmt.executeQuery()) {
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
    }

    return members; 
  }

  public Member detail(int no) throws Exception {
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
            "SELECT MNO,NAME,EMAIL,PWD,PHOTO,TEL,CDT FROM MEMBER WHERE MNO=?")) {

      stmt.setInt(1,  no);

      try (ResultSet rs = stmt.executeQuery()) {
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

  public Member detail(String email, String password) throws Exception {
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
            "SELECT MNO,NAME,EMAIL,PWD,PHOTO,TEL,CDT FROM MEMBER WHERE EMAIL=? AND PWD=?")) {
      
      stmt.setString(1, email);
      stmt.setString(2, password);
      
      try (ResultSet rs = stmt.executeQuery()) {
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
}
