package com.eomcs.lms.handler;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.domain.Member;

public class LoginCommand implements Command {

  SqlSessionFactory sqlSessionFactory;

  public LoginCommand(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void execute(BufferedReader in, PrintWriter out) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      out.print("이메일?\n!{}!\n"); out.flush();
      String email = in.readLine();

      out.print("암호?\n!{}!\n"); out.flush();
      String password = in.readLine();

      Map<String,Object> params = new HashMap<>();
      params.put("email", email);
      params.put("password", password);
      
      MemberDao memberDao = sqlSession.getMapper(MemberDao.class); 
      Member member = memberDao.detailByEmailPassword(params);

      if (member != null)
        out.printf("%s님 환영합니다!\n", member.getName());
      else
        out.println("이메일 또는 암호가 맞지 않습니다!");

    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }

  }
}
