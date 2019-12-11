package com.eomcs.lms.handler;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.domain.Member;
import com.eomcs.stereotype.CommandMapping;

@Component
public class AuthHandler {

  MemberDao memberDao;

  public AuthHandler(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @CommandMapping("/auth/login")
  public void login(BufferedReader in, PrintWriter out) {
    try {
      out.print("이메일?\n!{}!\n"); out.flush();
      String email = in.readLine();

      out.print("암호?\n!{}!\n"); out.flush();
      String password = in.readLine();

      Map<String,Object> params = new HashMap<>();
      params.put("email", email);
      params.put("password", password);
      
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
