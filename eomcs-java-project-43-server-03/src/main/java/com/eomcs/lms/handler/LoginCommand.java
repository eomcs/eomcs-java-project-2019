package com.eomcs.lms.handler;
import java.io.BufferedReader;
import java.io.PrintWriter;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.domain.Member;

public class LoginCommand implements Command {

  MemberDao memberDao;

  public LoginCommand(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @Override
  public void execute(BufferedReader in, PrintWriter out) {
    try {
      out.print("이메일?\n!{}!\n"); out.flush();
      String email = in.readLine();

      out.print("암호?\n!{}!\n"); out.flush();
      String password = in.readLine();

      Member member = memberDao.detail(email, password);

      if (member != null)
        out.printf("%s님 환영합니다!\n", member.getName());
      else
        out.println("이메일 또는 암호가 맞지 않습니다!");

    } catch (Exception e) {
      out.printf("%s : %s\n", e.toString(), e.getMessage());
    }

  }
}
