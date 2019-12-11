package com.eomcs.lms.dao;

import java.util.List;
import java.util.Map;
import com.eomcs.lms.domain.Member;

public interface MemberDao {

  int delete(int no) throws Exception;
  int update(Member member) throws Exception;
  int add(Member member) throws Exception;
  List<Member> list() throws Exception;
  List<Member> search(String keyword) throws Exception;
  Member detail(int no) throws Exception;
  Member detailByEmailPassword(Map<String,Object> params) throws Exception;
}
