package com.eomcs.lms.handler;
import java.io.BufferedReader;
import java.io.PrintWriter;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.domain.Member;

public class MemberDetailCommand implements Command {

  SqlSessionFactory sqlSessionFactory;

  public MemberDetailCommand(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void execute(BufferedReader in, PrintWriter out) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      out.print("번호?\n!{}!\n"); out.flush();
      int no = Integer.parseInt(in.readLine());

      MemberDao memberDao = sqlSession.getMapper(MemberDao.class); 
      Member member = memberDao.detail(no);
      if (member == null) { 
        out.println("해당 회원을 찾을 수 없습니다.");
        return;
      }

      out.printf("이름: %s\n", member.getName());
      out.printf("이메일: %s\n", member.getEmail());
      out.printf("암호: %s\n", member.getPassword());
      out.printf("사진: %s\n", member.getPhoto());
      out.printf("전화: %s\n", member.getTel());
      out.printf("가입일: %s\n", member.getRegisteredDate());

    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }
  }
}
