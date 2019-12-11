package com.eomcs.lms.handler;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.domain.Member;

public class MemberSearchCommand implements Command {
  
  SqlSessionFactory sqlSessionFactory;

  public MemberSearchCommand(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }
  
  @Override
  public void execute(BufferedReader in, PrintWriter out) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      out.print("검색어?\n!{}!\n");
      out.flush();
      String keyword = in.readLine();
      
      MemberDao memberDao = sqlSession.getMapper(MemberDao.class); 
      List<Member> members = memberDao.search(keyword);
      if (members == null) { 
        out.println("서버에서 데이터를 가져오는데 오류 발생!");
        return;
      }
      
      out.printf("검색 결과: %d 건\n", members.size());
      
      for (Member member : members) {
        out.printf("%3d, %-4s, %-20s, %-15s, %s\n", 
            member.getNo(), member.getName(), 
            member.getEmail(), member.getTel(), member.getRegisteredDate());
      }
      
    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }
}
