package com.eomcs.lms.handler;
import java.io.BufferedReader;
import java.io.PrintWriter;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.domain.Member;

public class MemberAddCommand implements Command {

  SqlSessionFactory sqlSessionFactory;

  public MemberAddCommand(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void execute(BufferedReader in, PrintWriter out) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Member member = new Member();

      out.print("이름?\n!{}!\n"); out.flush();
      member.setName(in.readLine());

      out.print("이메일?\n!{}!\n"); out.flush();
      member.setEmail(in.readLine());

      out.print("암호?\n!{}!\n"); out.flush();
      member.setPassword(in.readLine());

      out.print("사진?\n!{}!\n"); out.flush();
      member.setPhoto(in.readLine());

      out.print("전화?\n!{}!\n"); out.flush();
      member.setTel(in.readLine());

      MemberDao memberDao = sqlSession.getMapper(MemberDao.class); 
      memberDao.add(member);
      out.println("회원을 저장했습니다.");

      sqlSession.commit();
      
    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }
}
