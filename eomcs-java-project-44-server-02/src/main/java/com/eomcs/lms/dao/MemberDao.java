package com.eomcs.lms.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.eomcs.lms.domain.Member;

public class MemberDao {


  SqlSessionFactory sqlSessionFactory;

  public MemberDao(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  public int delete(int no) throws Exception {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      int count = session.delete("MemberMapper.delete", no);
      return count;
    } finally {
      session.close();
    }
  }

  public int update(Member member) throws Exception {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      int count = session.update("MemberMapper.update", member);
      return count;
    } finally {
      session.close();
    }
  }

  public int add(Member member) throws Exception {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      int count = session.insert("MemberMapper.add", member);
      return count;
    } finally {
      session.close();
    }
  }

  public List<Member> list() throws Exception {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      return session.selectList("MemberMapper.list");
    } finally {
      session.close();
    } 
  }

  public List<Member> search(String keyword) throws Exception {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      return session.selectList("MemberMapper.search", "%" + keyword + "%");
    } finally {
      session.close();
    } 
  }

  public Member detail(int no) throws Exception {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      return session.selectOne("MemberMapper.detail", no);
    } finally {
      session.close();
    }
  }

  public Member detail(String email, String password) throws Exception {
    SqlSession session = sqlSessionFactory.openSession();
    try {
      Map<String,Object> params = new HashMap<>();
      params.put("email", email);
      params.put("password", password);
      return session.selectOne("MemberMapper.detailByEmailPassword", params);
    } finally {
      session.close();
    }
  }
}
